package com.rendox.videoplayer.feature.feed

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object FeedRoute

fun NavGraphBuilder.feedRoute(
    navigateToPlayer: (String) -> Unit,
) {
    composable<FeedRoute> {
        FeedScreenStateful(openVideoDetails = navigateToPlayer)
    }
}