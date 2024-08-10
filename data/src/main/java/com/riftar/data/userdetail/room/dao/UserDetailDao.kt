package com.riftar.data.userdetail.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.riftar.data.userdetail.room.entity.UserDetailEntity
import com.riftar.data.userdetail.room.entity.UserDetailEntity.Companion.USER_DETAIL_TABLE

@Dao
interface UserDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notes: UserDetailEntity)

    @Query("DELETE FROM $USER_DETAIL_TABLE")
    suspend fun clearAll()

    @Query("SELECT * FROM $USER_DETAIL_TABLE WHERE id = :userId")
    suspend fun getUserDetailById(userId: Int): UserDetailEntity?

    @Transaction
    suspend fun insertAndGetUserDetail(userDetail: UserDetailEntity): UserDetailEntity? {
        insertAll(userDetail)
        return getUserDetailById(userDetail.id)
    }
}