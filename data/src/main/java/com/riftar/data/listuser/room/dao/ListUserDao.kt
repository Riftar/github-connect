package com.riftar.data.listuser.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.riftar.data.listuser.room.entity.UserEntity
import com.riftar.data.listuser.room.entity.UserEntity.Companion.LIST_USER_TABLE

@Dao
interface ListUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT * FROM $LIST_USER_TABLE WHERE id > :since ORDER BY id ASC LIMIT :limit")
    suspend fun getUsersAfter(since: Int, limit: Int): List<UserEntity>

    @Query("SELECT * FROM $LIST_USER_TABLE ORDER BY id ASC LIMIT 1")
    suspend fun getFirstUser(): UserEntity?

    @Query("SELECT * FROM $LIST_USER_TABLE ORDER BY id DESC LIMIT 1")
    suspend fun getLastUser(): UserEntity?

    @Query("DELETE FROM $LIST_USER_TABLE")
    suspend fun clearAll()

    @Query("SELECT * FROM $LIST_USER_TABLE ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, UserEntity>
}