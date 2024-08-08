package com.riftar.githubconnect.di.module

import android.content.Context
import androidx.room.Room
import com.riftar.data.common.database.AppDatabase
import com.riftar.data.common.notes.room.dao.NotesDao
import com.riftar.data.listuser.room.dao.ListUserDao
import com.riftar.data.userdetail.room.dao.UserDetailDao
import org.koin.dsl.module

val databaseModule = module {
    single { provideCustomerListDatabase(get()) }
    single { provideListUserDao(get()) }
    single { provideNotesDao(get()) }
    single { provideUserDetailDao(get()) }
}

fun provideCustomerListDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "github_connect_db")
        .fallbackToDestructiveMigration()
        .build()

fun provideListUserDao(db: AppDatabase): ListUserDao = db.getListUserDao()
fun provideNotesDao(db: AppDatabase): NotesDao = db.getNotesDao()
fun provideUserDetailDao(db: AppDatabase): UserDetailDao = db.getUserDetailDao()