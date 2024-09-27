package com.rendox.videoplayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.rendox.videoplayer.feature.feed.feedRoute
import com.rendox.videoplayer.feature.player.navigateToPlayer
import com.rendox.videoplayer.feature.player.playerRoute

@Composable
fun VpNavHost(
    modifier: Modifier = Modifier,
    startDestination: Any,
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        feedRoute(navigateToPlayer = { navController.navigateToPlayer(it) })
        playerRoute()
    }
}