package com.rendox.videoplayer.feature.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlayerScreenStateful(
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = koinViewModel(),
) {
    val screenState by viewModel.screenStateFlow.collectAsStateWithLifecycle()
    PlayerScreenStateless(
        modifier = modifier,
        screenState = screenState,
    )
}

@Composable
private fun PlayerScreenStateless(
    modifier: Modifier = Modifier,
    screenState: PlayerScreenState,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column {
            when (screenState) {
                is PlayerScreenState.Success -> {
                    val uriHandler = LocalUriHandler.current
                    Text(text = screenState.video.title)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.clickable {
                            uriHandler.openUri(screenState.video.url)
                        },
                        text = screenState.video.url,
                    )
                }

                is PlayerScreenState.Error -> {
                    Text(text = screenState.exception.message ?: "")
                }

                is PlayerScreenState.Loading -> Unit
            }
        }
    }
}