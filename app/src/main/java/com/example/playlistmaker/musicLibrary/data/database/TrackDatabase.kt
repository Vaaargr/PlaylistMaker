package com.example.playlistmaker.musicLibrary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.musicLibrary.data.dao.PlaylistDatabaseDao
import com.example.playlistmaker.musicLibrary.data.dao.PlaylistTrackDatabaseDao
import com.example.playlistmaker.musicLibrary.data.dao.TrackDatabaseDao
import com.example.playlistmaker.musicLibrary.data.entity.PlaylistEntity
import com.example.playlistmaker.musicLibrary.data.entity.PlaylistTrackEntity
import com.example.playlistmaker.musicLibrary.data.entity.TrackEntity

@Database(
    version = 8,
    entities = [TrackEntity::class, PlaylistEntity::class, PlaylistTrackEntity::class]
)
abstract class TrackDatabase : RoomDatabase() {

    abstract fun getTracksDao(): TrackDatabaseDao

    abstract fun getPlaylistsDao(): PlaylistDatabaseDao

    abstract fun getPlaylistTrackDao(): PlaylistTrackDatabaseDao
}