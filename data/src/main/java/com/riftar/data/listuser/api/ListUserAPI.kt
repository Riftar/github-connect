package com.riftar.data.listuser.api

import com.riftar.data.listuser.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ListUserAPI {
    @GET("users")
    suspend fun getListUser(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Response<List<UserResponse>>
}