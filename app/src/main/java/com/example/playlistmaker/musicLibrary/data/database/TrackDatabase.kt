package com.example.playlistmaker.musicLibrary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.musicLibrary.data.dao.PlaylistDatabaseDao
import com.example.playlistmaker.musicLibrary.data.dao.TrackDatabaseDao
import com.example.playlistmaker.musicLibrary.data.dao.TracksInPlaylistsDao
import com.example.playlistmaker.musicLibrary.data.entity.PlaylistEntity
import com.example.playlistmaker.musicLibrary.data.entity.TrackEntity
import com.example.playlistmaker.musicLibrary.data.entity.TracksInPlaylistsEntity

@Database(
    version = 5,
    entities = [TrackEntity::class, PlaylistEntity::class, TracksInPlaylistsEntity::class]
)
abstract class TrackDatabase : RoomDatabase() {

    abstract fun getTracksDao(): TrackDatabaseDao

    abstract fun getPlaylistsDao(): PlaylistDatabaseDao

    abstract fun getTracksInPlaylistDao(): TracksInPlaylistsDao
}