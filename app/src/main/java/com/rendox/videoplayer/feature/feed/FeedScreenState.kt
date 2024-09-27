package com.rendox.videoplayer.feature.feed

import com.rendox.videoplayer.model.VideoMetadata


sealed interface FeedScreenState {
    data object Loading : FeedScreenState
    data class Success(val videos: List<VideoMetadata>) : FeedScreenState
    data class Error(val exception: Exception) : FeedScreenState
}