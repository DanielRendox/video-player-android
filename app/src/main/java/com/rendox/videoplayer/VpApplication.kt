package com.rendox.videoplayer

import android.app.Application
import com.rendox.videoplayer.data.dataModule
import com.rendox.videoplayer.feature.player.playerScreenModule
import org.koin.core.context.startKoin

class VpApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                dataModule,
                playerScreenModule,
            )
        }
    }
}