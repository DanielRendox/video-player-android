package com.rendox.videoplayer.feature.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rendox.videoplayer.data.VideoRepository
import com.rendox.videoplayer.model.VpResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedViewModel(
    private val videoRepository: VideoRepository,
) : ViewModel() {

    private val _screenStateFlow = MutableStateFlow<FeedScreenState>(FeedScreenState.Loading)
    val screenStateFlow = _screenStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _screenStateFlow.update {
                when (val result = videoRepository.getVideos()) {
                    is VpResult.Success -> FeedScreenState.Success(videos = result.data)
                    is VpResult.Error -> FeedScreenState.Error(exception = result.exception)
                }
            }
        }
    }
}