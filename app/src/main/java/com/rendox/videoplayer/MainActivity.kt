package com.rendox.videoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rendox.videoplayer.feature.feed.FeedRoute
import com.rendox.videoplayer.ui.theme.VideoPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoPlayerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    VpNavHost(
                        modifier = Modifier.fillMaxSize(),
                        startDestination = FeedRoute,
                    )
                }
            }
        }
    }
}
