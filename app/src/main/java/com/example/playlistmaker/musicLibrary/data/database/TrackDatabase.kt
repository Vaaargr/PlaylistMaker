package com.example.playlistmaker.musicLibrary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.musicLibrary.data.dao.TrackDatabaseDao
import com.example.playlistmaker.musicLibrary.data.entity.TrackEntity

@Database(version = 2, entities = [TrackEntity::class])
abstract class TrackDatabase : RoomDatabase(){

    abstract fun getDao(): TrackDatabaseDao
}