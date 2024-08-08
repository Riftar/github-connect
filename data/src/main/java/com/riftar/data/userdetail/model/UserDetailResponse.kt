package com.riftar.data.userdetail.model


import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @SerializedName("bio")
    val bio: String? = null,
    @SerializedName("blog")
    val blog: String? = null,
    @SerializedName("company")
    val company: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("followers")
    val followers: Int? = null,
    @SerializedName("following")
    val following: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("twitter_username")
    val twitterUsername: String? = null
)