package com.rendox.videoplayer.feature.player

import androidx.lifecycle.ViewModel
import com.rendox.videoplayer.data.VideoRepository
import com.rendox.videoplayer.model.VpResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerViewModel(
    videoRepository: VideoRepository,
) : ViewModel() {

    private val _screenStateFlow = MutableStateFlow<PlayerScreenState>(PlayerScreenState.Loading)
    val screenStateFlow = _screenStateFlow.asStateFlow()

    init {
        _screenStateFlow.update {
            when (val result = videoRepository.getVideoByUrl("")) {
                is VpResult.Success -> PlayerScreenState.Success(video = result.data)
                is VpResult.Error -> PlayerScreenState.Error(exception = result.exception)
            }
        }
    }
}