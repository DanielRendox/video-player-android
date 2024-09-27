package com.rendox.videoplayer.network

import com.rendox.videoplayer.network.model.VideoMetadataNetwork
import retrofit2.http.GET

interface GitHubApi {

    @GET("videos.json")
    suspend fun getVideos(): List<VideoMetadataNetwork>
}