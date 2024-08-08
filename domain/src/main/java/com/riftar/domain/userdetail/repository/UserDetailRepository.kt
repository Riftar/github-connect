package com.riftar.domain.userdetail.repository

import com.riftar.domain.userdetail.model.UserDetail

interface UserDetailRepository {
    suspend fun getUserDetail(userId: Int, userName: String): Result<UserDetail>
    suspend fun saveNotes(userId: Int, notes: String): Result<Boolean>
}