package com.rendox.videoplayer.feature.feed.di

import com.rendox.videoplayer.feature.feed.FeedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val feedModule = module {
    viewModel {
        FeedViewModel(videoRepository = get())
    }
}