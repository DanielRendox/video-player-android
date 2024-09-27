package com.rendox.videoplayer.feature.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rendox.videoplayer.model.Video
import com.rendox.videoplayer.ui.theme.VideoPlayerTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlayerScreenStateful(
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = koinViewModel(),
) {
    val screenState by viewModel.screenStateFlow.collectAsStateWithLifecycle()
    PlayerScreenStateless(
        modifier = modifier,
        videoPlayer = {
            VideoPlayer(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9F),
                player = viewModel.player,
            )
        },
        screenState = screenState,
    )
}

@Composable
private fun PlayerScreenStateless(
    modifier: Modifier = Modifier,
    videoPlayer: @Composable () -> Unit,
    screenState: PlayerScreenState,
) {
    Column(modifier = modifier.windowInsetsPadding(WindowInsets.systemBars)) {
        when (screenState) {
            is PlayerScreenState.Success -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9F),
                    content = { videoPlayer() },
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = screenState.video.title,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = screenState.video.description,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }

            is PlayerScreenState.Error -> {
                Text(text = screenState.exception.message ?: "")
            }

            is PlayerScreenState.Loading -> Unit
        }
    }
}

@Preview
@Composable
private fun PlayerScreenStatePreview() {
    VideoPlayerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            PlayerScreenStateless(
                videoPlayer = {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Gray)
                    )
                },
                screenState = PlayerScreenState.Success(
                    video = Video(
                        url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                        thumbUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/images/BigBuckBunny.jpg",
                        title = "Big Buck Bunny",
                        subtitle = "By Blender Foundation",
                        description = "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.\n\nLicensed under the Creative Commons Attribution license\nhttp://www.bigbuckbunny.org",
                    )
                ),
            )
        }
    }
}