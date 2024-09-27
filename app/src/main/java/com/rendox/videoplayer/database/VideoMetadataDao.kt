package com.rendox.videoplayer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rendox.videoplayer.model.VideoMetadata

@Dao
interface VideoMetadataDao {

    @Insert
    suspend fun insertVideos(videos: List<VideoMetadataEntity>)

    @Query("SELECT * FROM video_metadata WHERE url = :url")
    suspend fun getVideoMetadata(url: String): VideoMetadataEntity?

    @Query("SELECT * FROM video_metadata")
    suspend fun getAllVideos(): List<VideoMetadataEntity>
}