package com.example.playlistmaker.musicLibrary.data.repositorys

import com.example.playlistmaker.musicLibrary.data.database.TrackDatabase
import com.example.playlistmaker.musicLibrary.data.mapper.PlaylistsEntityMapper
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.EditPlaylistRepository
import com.example.playlistmaker.musicLibrary.domain.entity.Playlist

class EditPlaylistRepositoryImpl(
    private val database: TrackDatabase,
    private val mapper: PlaylistsEntityMapper
) : EditPlaylistRepository {
    override suspend fun savePlaylist(playlist: Playlist) {
        database.getPlaylistsDao().savePlaylist(mapper.playlistToEntityMap(playlist))
    }

    override suspend fun loadPlaylist(id: Long): Playlist {
        return mapper.entityToPlaylistMap(database.getPlaylistsDao().loadPlaylist(id))
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        database.getPlaylistsDao().updatePlaylist(mapper.playlistToEntityMap(playlist))
    }
}