package com.riftar.data.userdetail.api

import com.riftar.data.userdetail.model.UserDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDetailAPI {
    @GET("users/{userName}")
    suspend fun getUserDetail(
        @Path("userName") userName: String
    ): Response<UserDetailResponse>
}