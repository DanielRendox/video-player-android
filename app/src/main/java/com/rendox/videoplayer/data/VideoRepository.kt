package com.rendox.videoplayer.data

import com.rendox.videoplayer.model.Video
import com.rendox.videoplayer.model.VpResult

interface VideoRepository {
    fun getVideoByUrl(url: String): VpResult<Video>
}