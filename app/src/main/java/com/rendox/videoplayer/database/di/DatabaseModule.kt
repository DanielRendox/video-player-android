package com.rendox.videoplayer.database.di

import androidx.room.Room
import com.rendox.videoplayer.database.VideoMetadataDao
import com.rendox.videoplayer.database.VpDatabase
import org.koin.dsl.module


val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            VpDatabase::class.java,
            "video_metadata_database"
        ).build()
    }

    single<VideoMetadataDao> {
        val database: VpDatabase = get()
        database.videoMetadataDao()
    }
}