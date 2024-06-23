package com.example.playlistmaker.musicLibrary.data.repositorys

import com.example.playlistmaker.musicLibrary.data.database.TrackDatabase
import com.example.playlistmaker.musicLibrary.data.mapper.PlaylistsEntityMapper
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.SavePlaylistRepository
import com.example.playlistmaker.musicLibrary.domain.entity.Playlist

class SavePlaylistRepositoryImpl(private val database: TrackDatabase, private val mapper: PlaylistsEntityMapper): SavePlaylistRepository {
    override suspend fun savePlaylist(playlist: Playlist) {
        database.getPlaylistsDao().savePlaylist(mapper.playlistToEntityMap(playlist))
    }
}