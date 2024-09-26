package com.rendox.videoplayer.feature.player

import com.rendox.videoplayer.model.Video

sealed interface PlayerScreenState {
    data object Loading : PlayerScreenState
    data class Success(val video: Video) : PlayerScreenState
    data class Error(val exception: Exception) : PlayerScreenState
}