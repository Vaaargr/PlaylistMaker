package com.example.playlistmaker.musicLibrary.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker.musicLibrary.presentation.ui.PlaylistsLibraryFragment
import com.example.playlistmaker.musicLibrary.presentation.ui.TracksLibraryFragment

class MusicLibraryPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       return when (position){
           0 -> TracksLibraryFragment.newInstance()
           else -> PlaylistsLibraryFragment.newInstance()
       }
    }

}