package com.rendox.videoplayer

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.rendox.videoplayer.data.dataModule
import com.rendox.videoplayer.database.di.databaseModule
import com.rendox.videoplayer.feature.feed.di.feedModule
import com.rendox.videoplayer.feature.player.di.playerScreenModule
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
                feedModule,
                databaseModule,
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