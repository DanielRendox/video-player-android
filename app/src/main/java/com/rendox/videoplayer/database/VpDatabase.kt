package com.rendox.videoplayer.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VideoMetadataEntity::class], version = 1, exportSchema = false)
abstract class VpDatabase : RoomDatabase() {
    abstract fun videoMetadataDao(): VideoMetadataDao
}