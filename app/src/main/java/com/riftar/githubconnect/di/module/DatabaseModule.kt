package com.riftar.githubconnect.di.module

import android.content.Context
import androidx.room.Room
import com.riftar.data.common.database.AppDatabase
import com.riftar.data.listuser.room.dao.ListUserDao
import org.koin.dsl.module

val databaseModule = module {
    single { provideCustomerListDatabase(get()) }
    single { provideListUserDao(get()) }
}

fun provideCustomerListDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "github_connect_db")
        .fallbackToDestructiveMigration()
        .build()

fun provideListUserDao(db: AppDatabase): ListUserDao = db.getListUserDao()