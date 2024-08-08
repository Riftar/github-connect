package com.riftar.data.userdetail.mapper

import com.riftar.data.userdetail.model.UserDetailResponse
import com.riftar.domain.userdetail.model.UserDetail

fun UserDetailResponse.toDomainModel() = UserDetail(
    avatarUrl = this.avatarUrl.orEmpty(),
    bio = this.bio.orEmpty(),
    blog = this.blog.orEmpty(),
    company = this.company.orEmpty(),
    email = this.email.orEmpty(),
    followers = this.followers ?: 0,
    following = this.following ?: 0,
    id = this.id ?: 0,
    location = this.location.orEmpty(),
    userName = this.login.orEmpty(),
    name = this.name.orEmpty(),
    twitterUsername = this.twitterUsername.orEmpty(),
)