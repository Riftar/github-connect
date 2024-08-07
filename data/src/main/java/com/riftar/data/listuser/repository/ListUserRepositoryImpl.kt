package com.riftar.data.listuser.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.riftar.data.common.database.AppDatabase
import com.riftar.data.listuser.api.ListUserAPI
import com.riftar.data.listuser.mapper.toDomainModel
import com.riftar.data.listuser.pagingsource.ListUserPagingSource
import com.riftar.data.listuser.remotemediator.ListUserRemoteMediator
import com.riftar.domain.listuser.model.User
import com.riftar.domain.listuser.repository.ListUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListUserRepositoryImpl(private val api: ListUserAPI, private val database: AppDatabase) :
    ListUserRepository {

    /***
     * This implementation is still contains bug.
     * The list will load data from internet even though there is a data already on the DB.
     * Need to fix it on the RemoteMediator level.
     */
    @OptIn(ExperimentalPagingApi::class)
    override fun getListUserRemoteMediator(): Flow<PagingData<User>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { database.getListUserDao().pagingSource() },
            remoteMediator = ListUserRemoteMediator(api, database)
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }

    override fun getListUser(): Flow<PagingData<User>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { ListUserPagingSource(api, database.getListUserDao()) }
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = INITIAL_LOAD_SIZE
        )
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 30
        const val INITIAL_LOAD_SIZE = 60
        const val PROFILE_IMAGE_CACHE_RESOLUTION = 500
    }
}