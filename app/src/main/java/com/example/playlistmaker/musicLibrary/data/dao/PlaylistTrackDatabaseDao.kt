package com.example.playlistmaker.musicLibrary.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.musicLibrary.data.entity.PlaylistTrackEntity
import com.example.playlistmaker.musicLibrary.data.entity.TrackEntity

@Dao
interface PlaylistTrackDatabaseDao {

    @Insert(PlaylistTrackEntity::class, OnConflictStrategy.IGNORE)
    suspend fun saveTrackInPlaylist(playlistTrackEntity: PlaylistTrackEntity)

    @Query("DELETE FROM playlist_track WHERE playlistID = :playlistID AND trackID = :trackID")
    suspend fun deleteTrackFromPlaylist(playlistID: Long, trackID: Long)

    @Query("SELECT COUNT(playlistID) FROM playlist_track WHERE trackID = :trackID")
    suspend fun countTrackInPlaylists(trackID: Long): Int

    @Query("SELECT COUNT(playlistID) FROM playlist_track WHERE playlistID = :playlistID AND trackID = :trackID")
    suspend fun checkTrackInPlaylist(playlistID: Long, trackID: Long): Int

    @Query("SELECT playlistID FROM playlist_track WHERE trackID = :trackID")
    suspend fun checkTrackInAllPlaylists(trackID: Long): List<Long>

    @Query("SELECT * FROM saved_track_table INNER JOIN playlist_track ON saved_track_table.trackId = playlist_track.trackID WHERE playlist_track.playlistID = :playlistID ORDER BY playlist_track.position DESC")
    suspend fun getAllTracksInPlaylist(playlistID: Long): List<TrackEntity>

    @Query("SELECT MAX(position) FROM playlist_track WHERE playlistID = :playlistID")
    suspend fun getMaxPosition(playlistID: Long): Int?
}