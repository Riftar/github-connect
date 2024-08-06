package com.riftar.data.listuser.mapper

import com.riftar.data.listuser.model.UserResponse
import com.riftar.domain.listuser.model.User

fun UserResponse.toDomainModel() = User(
    avatarUrl = this.avatarUrl.orEmpty(),
    htmlUrl = this.htmlUrl.orEmpty(),
    id = this.id ?: 0,
    userName = this.login.orEmpty()
)