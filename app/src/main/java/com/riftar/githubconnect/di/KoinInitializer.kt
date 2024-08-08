package com.riftar.githubconnect.di

import android.content.Context
import com.riftar.githubconnect.di.module.dataModule
import com.riftar.githubconnect.di.module.databaseModule
import com.riftar.githubconnect.di.module.domainModule
import com.riftar.githubconnect.di.module.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object KoinInitializer {

    fun init(context: Context) {
        startKoin {
            androidContext(context)

            modules(
                databaseModule,
                dataModule,
                domainModule,
                viewModule,
            )
        }
    }
}