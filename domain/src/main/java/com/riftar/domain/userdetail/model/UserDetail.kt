package com.riftar.domain.userdetail.model

data class UserDetail(
    val avatarUrl: String,
    val bio: String,
    val blog: String,
    val company: String,
    val email: String,
    val followers: Int,
    val following: Int,
    val id: Int,
    val location: String,
    val userName: String,
    val name: String,
    val twitterUsername: String,
    val notes: String
)