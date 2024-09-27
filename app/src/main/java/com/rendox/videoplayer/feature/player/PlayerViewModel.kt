package com.rendox.videoplayer.feature.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.rendox.videoplayer.data.VideoRepository
import com.rendox.videoplayer.model.VpResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerViewModel(
    initialVideoUrl: String,
    videoRepository: VideoRepository,
    val player: Player,
) : ViewModel() {

    private val _screenStateFlow = MutableStateFlow<PlayerScreenState>(PlayerScreenState.Loading)
    val screenStateFlow = _screenStateFlow.asStateFlow()

    init {
        player.prepare()
        viewModelScope.launch {
            when (val result = videoRepository.getVideos()) {
                is VpResult.Success -> {
                    player.addMediaItems(result.data.map { MediaItem.fromUri(it.url) })
                    player.setMediaItem(MediaItem.fromUri(initialVideoUrl))
                    player.play()
                }

                is VpResult.Error -> _screenStateFlow.update {
                    PlayerScreenState.Error(exception = result.exception)
                }
            }
        }
        viewModelScope.launch {
            _screenStateFlow.update {
                when (val result = videoRepository.getVideoByUrl(initialVideoUrl)) {
                    is VpResult.Success -> {
                        if (result.data != null) {
                            PlayerScreenState.Success(video = result.data)
                        } else {
                            PlayerScreenState.Error(exception = VideoNotFoundException())
                        }
                    }

                    is VpResult.Error -> {
                        PlayerScreenState.Error(exception = result.exception)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        player.stop()
        super.onCleared()
    }
}