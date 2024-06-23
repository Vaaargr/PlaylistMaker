package com.example.playlistmaker.musicLibrary.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playlistmaker.musicLibrary.data.entity.PlaylistEntity

@Dao
interface PlaylistDatabaseDao {

    @Insert(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlaylist(playlist: PlaylistEntity)

    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deleteTrack(id: Int)

    @Query("SELECT * FROM playlists ORDER BY id DESC")
    suspend fun getAllPlaylists(): List<PlaylistEntity>

    @Update(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePlaylist(playlist: PlaylistEntity)
}