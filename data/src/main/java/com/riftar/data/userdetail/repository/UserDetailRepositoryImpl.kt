package com.riftar.data.userdetail.repository

import com.riftar.data.userdetail.api.UserDetailAPI
import com.riftar.data.userdetail.mapper.toDomainModel
import com.riftar.domain.userdetail.model.UserDetail
import com.riftar.domain.userdetail.repository.UserDetailRepository

class UserDetailRepositoryImpl(private val api: UserDetailAPI) : UserDetailRepository {
    override suspend fun getUserDetail(userName: String): Result<UserDetail> {
        return try {
            val response = api.getUserDetail(userName)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Result.success(result.toDomainModel())
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}