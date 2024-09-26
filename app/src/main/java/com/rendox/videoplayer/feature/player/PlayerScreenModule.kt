package com.rendox.videoplayer.feature.player

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val playerScreenModule = module {
    viewModel {
        PlayerViewModel(
            videoRepository = get()
        )
    }
}