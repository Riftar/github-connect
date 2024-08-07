package com.riftar.githubconnect

import android.app.Application
import com.riftar.githubconnect.di.KoinInitializer

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer.init(this)
    }
}