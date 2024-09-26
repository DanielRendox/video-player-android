package com.rendox.videoplayer.data

import org.koin.dsl.module

val dataModule = module {
    single<VideoRepository> {
        VideoRepositoryImpl()
    }
}