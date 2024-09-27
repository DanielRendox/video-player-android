package com.rendox.videoplayer.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.rendox.videoplayer.R

@Composable
fun VideoThumbnail(
    modifier: Modifier = Modifier,
    thumbUrl: String,
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = thumbUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
    ) {
        when (val state = painter.state) {
            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
            is AsyncImagePainter.State.Loading, AsyncImagePainter.State.Empty -> {
                Box(modifier = Modifier.background(color = Color.Gray))
            }

            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier.background(color = Color.Black),
                    contentAlignment = Alignment.Center,
                ) {
                    val errorMessage = stringResource(R.string.error_loading_image) + ".\n" +
                            state.result.throwable.localizedMessage
                    Text(
                        text = errorMessage,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}