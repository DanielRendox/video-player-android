package com.rendox.videoplayer.feature.player

import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rendox.videoplayer.R
import com.rendox.videoplayer.model.VideoMetadata
import com.rendox.videoplayer.ui.theme.VideoPlayerTheme
import com.rendox.videoplayer.ui.theme.components.ErrorDisplay

@Composable
fun PlayerScreenStateful(
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel,
) {
    val screenState by viewModel.screenStateFlow.collectAsStateWithLifecycle()

    PlayerScreenStateless(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        videoPlayer = {
            VideoPlayer(
                modifier = Modifier.fillMaxSize(),
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
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val context = LocalContext.current
    val window = (context as ComponentActivity).window

    LaunchedEffect(isLandscape) {
        if (isLandscape) hideSystemUi(window)
    }

    Column(modifier = modifier) {
        when (screenState) {
            is PlayerScreenState.Success -> {
                Box(
                    modifier = if (isLandscape) {
                        Modifier.fillMaxSize()
                    } else {
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9F)
                    },
                    content = { videoPlayer() },
                )
                if (!isLandscape) {
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
            }

            is PlayerScreenState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    ErrorDisplay(
                        errorMessage = screenState.exception.localizedMessage,
                        description = stringResource(R.string.could_not_connect_error_message),
                    )
                }
            }

            is PlayerScreenState.Loading -> Unit
        }
    }
}

@Suppress("DEPRECATION")
private fun hideSystemUi(window: Window) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.let { controller ->
            controller.hide(android.view.WindowInsets.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
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
                    video = VideoMetadata(
                        url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                        thumbUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
                        title = "Big Buck Bunny",
                        subtitle = "By Blender Foundation",
                        description = "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.\n\nLicensed under the Creative Commons Attribution license\nhttp://www.bigbuckbunny.org",
                    )
                ),
            )
        }
    }
}