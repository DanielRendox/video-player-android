package com.rendox.videoplayer.feature.player

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class PlayerRoute(val videoUrl: String)

fun NavController.navigateToPlayer(
    videoUrl: String,
    navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}
) = this.navigate(PlayerRoute(videoUrl), navOptionsBuilder)

fun NavGraphBuilder.playerRoute() {
    composable<PlayerRoute> { navBackStackEntry ->
        val args = navBackStackEntry.toRoute<PlayerRoute>()
        PlayerScreenStateful(
            viewModel = koinViewModel(parameters = { parametersOf(args.videoUrl) }),
        )
    }
}

