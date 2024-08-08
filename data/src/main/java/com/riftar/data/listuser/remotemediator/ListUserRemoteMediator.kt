package com.riftar.data.listuser.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.riftar.data.common.database.AppDatabase
import com.riftar.data.listuser.api.ListUserAPI
import com.riftar.data.listuser.mapper.toEntity
import com.riftar.data.listuser.room.entity.UserEntity

@OptIn(ExperimentalPagingApi::class)
class ListUserRemoteMediator(
    private val api: ListUserAPI,
    private val database: AppDatabase
) : RemoteMediator<Int, UserEntity>() {

    private val userDao = database.getListUserDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.id
                }
            }

            val response = api.getListUser(since = loadKey ?: 0, perPage = state.config.pageSize)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDao.clearAll()
                }
                val users = response.body().orEmpty().map { it.toEntity() }
                userDao.insertAll(users)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.body().orEmpty().isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}