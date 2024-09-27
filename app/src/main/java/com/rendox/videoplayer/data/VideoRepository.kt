package com.rendox.videoplayer.data

import com.rendox.videoplayer.model.VideoMetadata
import com.rendox.videoplayer.model.VpResult

interface VideoRepository {
    suspend fun getVideos(): VpResult<List<VideoMetadata>>
    suspend fun getVideoByUrl(url: String): VpResult<VideoMetadata?>
}