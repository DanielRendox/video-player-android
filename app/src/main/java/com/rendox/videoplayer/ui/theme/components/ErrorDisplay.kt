package com.rendox.videoplayer.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rendox.videoplayer.R
import com.rendox.videoplayer.ui.theme.VideoPlayerTheme

@Composable
fun ErrorDisplay(
    modifier: Modifier = Modifier,
    errorMessage: String?,
    description: String,
) {
    Column(
        modifier = modifier.width(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            modifier = Modifier.size(80.dp),
            painter = painterResource(id = R.drawable.warning),
            contentDescription = null,
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
        }
        Text(
            text = description,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun ErrorDisplayPreview() {
    VideoPlayerTheme {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                ErrorDisplay(
                    modifier = Modifier,
                    errorMessage = "Error 404",
                    description = "Something went wrong, please check your internet connection",
                )
            }
        }
    }
}