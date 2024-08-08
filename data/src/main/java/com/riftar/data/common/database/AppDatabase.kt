package com.riftar.data.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.riftar.data.common.notes.room.dao.NotesDao
import com.riftar.data.common.notes.room.entity.NotesEntity
import com.riftar.data.listuser.room.dao.ListUserDao
import com.riftar.data.listuser.room.entity.UserEntity
import com.riftar.data.userdetail.room.dao.UserDetailDao
import com.riftar.data.userdetail.room.entity.UserDetailEntity

@Database(
    entities = [UserEntity::class, NotesEntity::class, UserDetailEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getListUserDao(): ListUserDao
    abstract fun getNotesDao(): NotesDao
    abstract fun getUserDetailDao(): UserDetailDao
}