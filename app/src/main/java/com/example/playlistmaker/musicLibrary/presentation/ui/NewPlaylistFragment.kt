package com.example.playlistmaker.musicLibrary.presentation.ui

import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.NewPlaylistFragmentBinding
import com.example.playlistmaker.musicLibrary.presentation.viewModel.NewPlaylistFragmentViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.io.File
import java.io.FileOutputStream

class NewPlaylistFragment : Fragment() {
    private var _binding: NewPlaylistFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModel<NewPlaylistFragmentViewModel>()
    val requester = PermissionRequester.instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewPlaylistFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                viewModel.changeImage(uri)
            }

        viewModel.observeButtonState().observe(viewLifecycleOwner) { state ->
            binding.createPlaylist.isEnabled = state.isEnable
        }

        viewModel.observeImageLD().observe(viewLifecycleOwner) { uri ->
            if (uri != null) {
                binding.playlistImage.setImageURI(uri)
            } else {
                binding.playlistImage.setImageResource(R.drawable.new_playlist_placeholder)
            }
        }

        with(binding) {
            playlistName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.nameChanged(s.toString())
                }
            })

            playlistDescription.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.setDescription(s.toString())
                }
            })

            playlistImage.setOnClickListener {
                lifecycleScope.launch {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requester.request(READ_MEDIA_IMAGES).collect { result ->
                            when (result) {
                                is PermissionResult.Granted -> {
                                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }

                                is PermissionResult.Denied.NeedsRationale -> {
                                    Toast.makeText(
                                        requireContext(),
                                        R.string.permission_rationale,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                is PermissionResult.Denied.DeniedPermanently -> {
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    intent.data =
                                        Uri.fromParts("package", requireContext().packageName, null)
                                    requireContext().startActivity(intent)
                                }

                                PermissionResult.Cancelled -> return@collect
                            }
                        }
                    } else {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                }

            }

            npBackButton.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            createPlaylist.setOnClickListener {
                viewModel.prepareAndSavePlaylist(saveImage())

                val toastText =
                    "${getString(R.string.np_message_begin)} ${viewModel.getName()} ${getString(R.string.np_message_end)}"
                val container = requireActivity().findViewById<ViewGroup>(R.id.toast_container)
                val layout = layoutInflater.inflate(R.layout.toast, container)
                layout.findViewById<TextView>(R.id.toast_text).text = toastText
                val toast = Toast(requireContext())
                toast.duration = Toast.LENGTH_LONG
                toast.view = layout
                toast.show()

                viewModel.clearDate()
                findNavController().navigateUp()
            }
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewModel.checkData()) {
                        findNavController().navigateUp()
                    } else {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(R.string.new_playlist_finish)
                            .setMessage(R.string.new_playlist_warning)
                            .setPositiveButton(R.string.complete) { _, _ ->
                                viewModel.clearDate()
                                findNavController().navigateUp()
                            }
                            .setNegativeButton(R.string.cancel) { _, _ -> }
                            .show()
                    }
                }
            })
    }

    fun saveImage(): String? {
        val uri = viewModel.getUri()
        val name = viewModel.getName()

        if (uri != null && !name.isNullOrEmpty()) {
            val filePath =
                File(
                    requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "myalbum"
                )

            if (!filePath.exists()) {
                filePath.mkdirs()
            }

            val file = File(filePath, "${name}.jpg")
            val inputStream =
                requireContext().applicationContext.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)

            BitmapFactory
                .decodeStream(inputStream)
                .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)

            return file.absolutePath
        } else {
            return null
        }
    }
}