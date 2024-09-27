package com.rendox.videoplayer.data

import com.rendox.videoplayer.model.VideoMetadata
import com.rendox.videoplayer.model.VpResult

interface VideoRepository {
    fun getVideoByUrl(url: String): VpResult<VideoMetadata>
}