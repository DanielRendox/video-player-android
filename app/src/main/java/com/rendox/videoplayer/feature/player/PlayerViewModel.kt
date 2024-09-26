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
    videoRepository: VideoRepository,
    val player: Player,
) : ViewModel() {

    private val _screenStateFlow = MutableStateFlow<PlayerScreenState>(PlayerScreenState.Loading)
    val screenStateFlow = _screenStateFlow.asStateFlow()

    init {
        player.prepare()
        viewModelScope.launch {
            when (val result = videoRepository.getVideoByUrl("")) {
                is VpResult.Success -> {
                    val mediaItem = MediaItem.fromUri(result.data.url)
                    player.setMediaItem(mediaItem)
                    player.play()
                    _screenStateFlow.update { PlayerScreenState.Success(video = result.data) }
                }
                is VpResult.Error -> _screenStateFlow.update {
                    PlayerScreenState.Error(exception = result.exception)
                }
            }
        }
    }
}