package com.riftar.data.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.riftar.data.listuser.room.dao.ListUserDao
import com.riftar.data.listuser.room.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getListUserDao(): ListUserDao
}