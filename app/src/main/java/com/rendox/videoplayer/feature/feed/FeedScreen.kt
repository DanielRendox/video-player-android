package com.rendox.videoplayer.feature.feed

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.rendox.videoplayer.R
import com.rendox.videoplayer.model.VideoMetadata
import com.rendox.videoplayer.ui.theme.VideoPlayerTheme
import com.rendox.videoplayer.ui.theme.components.ErrorDisplay
import com.rendox.videoplayer.ui.theme.components.VideoThumbnail
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedScreenStateful(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = koinViewModel(),
    openVideoDetails: (String) -> Unit,
) {
    val screenState by viewModel.screenStateFlow.collectAsStateWithLifecycle()
    FeedScreenStateless(
        modifier = modifier,
        screenState = screenState,
        videoThumbnail = { thumbUrl ->
            VideoThumbnail(
                modifier = Modifier.fillMaxSize(),
                thumbUrl = thumbUrl,
            )
        },
        openVideoDetails = openVideoDetails,
    )
}

@Composable
private fun FeedScreenStateless(
    modifier: Modifier = Modifier,
    screenState: FeedScreenState,
    videoThumbnail: @Composable (String) -> Unit,
    openVideoDetails: (String) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .height(64.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    ) { contentPadding ->
        when (screenState) {
            is FeedScreenState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(
                        items = screenState.videos,
                        key = { it.url },
                    ) { video ->
                        val safelyOpenVideoDetails = dropUnlessResumed {
                            openVideoDetails(video.url)
                        }

                        if (isLandscape) {
                            FeedItemLandscape(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .clickable(onClick = safelyOpenVideoDetails),
                                videoThumbnail = videoThumbnail,
                                video = video,
                            )
                        } else {
                            FeedItemPortrait(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = safelyOpenVideoDetails),
                                videoThumbnail = videoThumbnail,
                                video = video,
                            )
                        }
                    }
                }
            }

            is FeedScreenState.Error -> {
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

            is FeedScreenState.Loading -> Unit
        }
    }
}

@Composable
private fun FeedItemPortrait(
    modifier: Modifier = Modifier,
    videoThumbnail: @Composable (String) -> Unit,
    video: VideoMetadata,
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9F)
        ) {
            videoThumbnail(video.thumbUrl)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 16.dp),
            text = video.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = video.subtitle,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
        )
    }
}

@Composable
private fun FeedItemLandscape(
    modifier: Modifier = Modifier,
    videoThumbnail: @Composable (String) -> Unit,
    video: VideoMetadata,
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .aspectRatio(16 / 9F)
        ) {
            videoThumbnail(video.thumbUrl)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                overflow = TextOverflow.Ellipsis,
                text = video.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = video.subtitle,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
            )
        }
    }
}

@PreviewScreenSizes
@Composable
private fun FeedScreenPreview(
    @PreviewParameter(provider = LoremIpsum::class) loremIpsum: String,
) {
    VideoPlayerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            FeedScreenStateless(
                screenState = FeedScreenState.Success(
                    videos = List(10) { index ->
                        VideoMetadata(
                            url = index.toString(),
                            thumbUrl = "",
                            title = loremIpsum,
                            subtitle = loremIpsum,
                            description = loremIpsum,
                        )
                    }
                ),
                videoThumbnail = {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Gray)
                    )
                },
                openVideoDetails = {},
            )
        }
    }
}