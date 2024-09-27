package com.rendox.videoplayer.feature.player

import com.rendox.videoplayer.model.VideoMetadata

sealed interface PlayerScreenState {
    data object Loading : PlayerScreenState
    data class Success(val video: VideoMetadata) : PlayerScreenState
    data class Error(val exception: Exception) : PlayerScreenState
}