package com.riftar.data.userdetail.mapper

import com.riftar.data.userdetail.model.UserDetailResponse
import com.riftar.data.userdetail.room.entity.UserDetailEntity
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
    notes = ""
)

fun UserDetailResponse.toEntity() = UserDetailEntity(
    avatarUrl = this.avatarUrl,
    bio = this.bio,
    blog = this.blog,
    company = this.company,
    email = this.email,
    followers = this.followers,
    following = this.following,
    id = this.id ?: 0,
    location = this.location,
    userName = this.login,
    name = this.name,
    twitterUsername = this.twitterUsername
)

fun UserDetailEntity?.toDomainModel(notes: String) = UserDetail(
    avatarUrl = this?.avatarUrl.orEmpty(),
    bio = this?.bio.orEmpty(),
    blog = this?.blog.orEmpty(),
    company = this?.company.orEmpty(),
    email = this?.email.orEmpty(),
    followers = this?.followers ?: 0,
    following = this?.following ?: 0,
    id = this?.id ?: 0,
    location = this?.location.orEmpty(),
    userName = this?.userName.orEmpty(),
    name = this?.name.orEmpty(),
    twitterUsername = this?.twitterUsername.orEmpty(),
    notes = notes
)