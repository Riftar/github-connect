package com.riftar.domain.userdetail.repository

import com.riftar.domain.userdetail.model.UserDetail

interface UserDetailRepository {
    suspend fun getUserDetail(userName: String): Result<UserDetail>
}