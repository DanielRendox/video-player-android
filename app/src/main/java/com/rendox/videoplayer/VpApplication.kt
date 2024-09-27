package com.rendox.videoplayer

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.rendox.videoplayer.data.dataModule
import com.rendox.videoplayer.feature.player.playerScreenModule
import com.rendox.videoplayer.network.di.networkApiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VpApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VpApplication)
            modules(
                networkApiModule,
                dataModule,
                playerScreenModule,
            )
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .logger(DebugLogger())
            .build()
    }
}