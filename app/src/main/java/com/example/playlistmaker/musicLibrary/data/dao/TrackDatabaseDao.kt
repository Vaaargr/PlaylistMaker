package com.example.playlistmaker.musicLibrary.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.musicLibrary.data.entity.TrackEntity

@Dao
interface TrackDatabaseDao {

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrack(track: TrackEntity)

    @Query("DELETE FROM saved_track_table WHERE trackId = :trackID")
    suspend fun deleteTrack(trackID: Int)

    @Query("SELECT * FROM saved_track_table ORDER BY id DESC")
    suspend fun getAllSavedTracks(): List<TrackEntity>

    @Query("SELECT * FROM saved_track_table WHERE trackId = :trackID")
    suspend fun findTrack(trackID: Int): List<TrackEntity>
}