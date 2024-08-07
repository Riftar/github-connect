package com.riftar.data.listuser.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.riftar.data.listuser.api.ListUserAPI
import com.riftar.data.listuser.pagingsource.ListUserPagingSource
import com.riftar.data.listuser.pagingsource.ListUserPagingSource.Companion.DEFAULT_PAGE_SIZE
import com.riftar.data.listuser.pagingsource.ListUserPagingSource.Companion.INITIAL_LOAD_SIZE
import com.riftar.domain.listuser.model.User
import com.riftar.domain.listuser.repository.ListUserRepository
import kotlinx.coroutines.flow.Flow

class ListUserRepositoryImpl(private val api: ListUserAPI) : ListUserRepository {
    override fun getListUser(): Flow<PagingData<User>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { ListUserPagingSource(api) }
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = INITIAL_LOAD_SIZE
        )
    }
}