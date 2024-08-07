package com.riftar.domain.listuser.repository

import androidx.paging.PagingData
import com.riftar.domain.listuser.model.User
import kotlinx.coroutines.flow.Flow

interface ListUserRepository {
    fun getListUser(): Flow<PagingData<User>>
}