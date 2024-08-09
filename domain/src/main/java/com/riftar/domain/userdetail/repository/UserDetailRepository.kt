package com.riftar.domain.userdetail.repository

import com.riftar.domain.userdetail.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserDetailRepository {
    fun getFlowUserDetail(userId: Int, userName: String): Flow<Result<UserDetail>>
    fun saveNotes(userId: Int, notes: String): Flow<Result<Unit>>
}