package com.example.playlistmaker.musicLibrary.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.playlistmaker.musicLibrary.data.entity.TracksInPlaylistsEntity

@Dao
interface TracksInPlaylistsDao {

    @Insert(entity = TracksInPlaylistsEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveTrack(track: TracksInPlaylistsEntity)
}