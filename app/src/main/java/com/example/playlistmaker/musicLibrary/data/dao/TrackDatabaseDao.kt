package com.example.playlistmaker.musicLibrary.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playlistmaker.musicLibrary.data.entity.TrackEntity

@Dao
interface TrackDatabaseDao {

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveTrack(track: TrackEntity)

    @Query("DELETE FROM saved_track_table WHERE trackId = :trackID")
    suspend fun deleteTrack(trackID: Long)

    @Query("SELECT * FROM saved_track_table ORDER BY innerID DESC")
    suspend fun getAllSavedTracks(): List<TrackEntity>

    @Query("SELECT * FROM saved_track_table WHERE trackId = :trackID")
    suspend fun findTrack(trackID: Long): TrackEntity

    @Update(entity = TrackEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTrack(track: TrackEntity)

    @Query("SELECT * FROM saved_track_table WHERE isLicked = 1 ORDER BY innerID DESC")
    suspend fun getAllLikedTracks(): List<TrackEntity>
}