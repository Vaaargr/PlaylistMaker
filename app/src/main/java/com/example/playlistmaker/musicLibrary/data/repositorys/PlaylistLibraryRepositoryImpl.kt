package com.example.playlistmaker.musicLibrary.data.repositorys

import com.example.playlistmaker.musicLibrary.data.database.TrackDatabase
import com.example.playlistmaker.musicLibrary.data.entity.PlaylistEntity
import com.example.playlistmaker.musicLibrary.data.mapper.PlaylistsEntityMapper
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.PlaylistLibraryRepository
import com.example.playlistmaker.musicLibrary.domain.entity.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistLibraryRepositoryImpl(
    private val database: TrackDatabase,
    private val mapper: PlaylistsEntityMapper
) : PlaylistLibraryRepository {
    override fun getSavedPlaylists(): Flow<List<Playlist>> = flow {
        val playlists = database.getPlaylistsDao().getAllPlaylists()
        emit(listEntityToListPlaylist(playlists))
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        database.getPlaylistsDao().updatePlaylist(mapper.playlistToEntityMap(playlist))
    }

    private fun listEntityToListPlaylist(input: List<PlaylistEntity>): List<Playlist>{
        return input.map { mapper.entityToPlaylistMap(it) }
    }
}