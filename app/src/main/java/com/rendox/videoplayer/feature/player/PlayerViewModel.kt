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
                    val mediaItems = result.data.map { MediaItem.fromUri(it.url) }
                    player.addMediaItems(mediaItems)

                    val initialMediaIndex = mediaItems.indexOfFirst {
                        it.localConfiguration?.uri.toString() == initialVideoUrl
                    }
                    if (initialMediaIndex >= 0) {
                        player.seekTo(initialMediaIndex, 0L)
                    }

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

        // Add listener to handle media item transitions (for Previous/Next button actions)
        player.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                // Handle media item transition here (e.g., updating UI)
                mediaItem?.let {
                    viewModelScope.launch {
                        val videoDetails = videoRepository.getVideoByUrl(it.localConfiguration?.uri.toString())
                        if (videoDetails is VpResult.Success && videoDetails.data != null) {
                            _screenStateFlow.update {
                                PlayerScreenState.Success(video = videoDetails.data)
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onCleared() {
        player.release() // Release player resources when ViewModel is cleared
        super.onCleared()
    }
}
