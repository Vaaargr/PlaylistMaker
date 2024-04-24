package com.example.playlistmaker.settings.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.SettingsFragmentBinding
import com.example.playlistmaker.settings.presentation.viewModel.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getThemeSettings().observe(viewLifecycleOwner) { themeSettings ->
            binding.darkThemeSwitch.isChecked = themeSettings.nightTheme
        }

        with(binding) {
            darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
                viewModel.changeNightMode(isChecked)
            }

            shareAppButton.setOnClickListener {
                viewModel.shareApp()
            }

            supportButton.setOnClickListener {
                viewModel.openSupport()
            }

            termsButton.setOnClickListener {
                viewModel.openTerms()
            }
        }
    }
}