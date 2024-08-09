package com.riftar.data.userdetail.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.riftar.data.userdetail.room.entity.UserDetailEntity.Companion.USER_DETAIL_TABLE

@Entity(tableName = USER_DETAIL_TABLE)
data class UserDetailEntity(
    @PrimaryKey
    val id: Int,
    val avatarUrl: String?,
    val bio: String?,
    val blog: String?,
    val company: String?,
    val email: String?,
    val followers: Int?,
    val following: Int?,
    val location: String?,
    val userName: String?,
    val name: String?,
    val twitterUsername: String?
) {
    companion object {
        const val USER_DETAIL_TABLE = "user_detail"
    }
}