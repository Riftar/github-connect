package com.riftar.data.listuser.mapper

import com.riftar.data.listuser.model.UserResponse
import com.riftar.data.listuser.room.entity.UserEntity
import com.riftar.domain.listuser.model.User

fun UserResponse.toDomainModel() = User(
    avatarUrl = this.avatarUrl.orEmpty(),
    htmlUrl = this.htmlUrl.orEmpty(),
    id = this.id ?: 0,
    userName = this.login.orEmpty(),
    hasNotes = false
)

fun UserResponse.toEntity() = UserEntity(
    avatarUrl = this.avatarUrl,
    htmlUrl = this.htmlUrl,
    id = this.id ?: 0,
    userName = this.login
)

fun UserEntity.toDomainModel(hasNotes: Boolean) = User(
    avatarUrl = this.avatarUrl.orEmpty(),
    htmlUrl = this.htmlUrl.orEmpty(),
    id = this.id,
    userName = this.userName.orEmpty(),
    hasNotes = hasNotes
)