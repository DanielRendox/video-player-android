package com.rendox.videoplayer.feature.player

import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val playerScreenModule = module {
    viewModel { parameters ->
        PlayerViewModel(
            initialVideoUrl = parameters.get(),
            videoRepository = get(),
            player = get()
        )
    }

    single<Player> {
        ExoPlayer.Builder(get()).build()
    }
}