package com.riftar.data.listuser.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.riftar.data.listuser.room.entity.UserEntity.Companion.LIST_USER_TABLE

@Entity(tableName = LIST_USER_TABLE)
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val avatarUrl: String?,
    val htmlUrl: String?,
    val userName: String?,
    val hasNotes: Boolean = false
) {
    companion object {
        const val LIST_USER_TABLE = "list_user"
    }
}