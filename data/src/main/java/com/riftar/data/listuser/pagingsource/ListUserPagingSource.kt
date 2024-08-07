package com.riftar.data.listuser.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.riftar.data.listuser.api.ListUserAPI
import com.riftar.data.listuser.mapper.toDomainModel
import com.riftar.domain.listuser.model.User

class ListUserPagingSource(
    private val api: ListUserAPI
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val lastUserId = params.key ?: 0
            val perPage = DEFAULT_PAGE_SIZE
            val response = api.getListUser(
                since = lastUserId,
                perPage = perPage
            )
            if (response.isSuccessful) {
                val users = response.body().orEmpty().map { it.toDomainModel() }
                LoadResult.Page(
                    data = users,
                    prevKey = null,
                    nextKey = users.lastOrNull()?.id
                )
            } else {
                LoadResult.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 30
        const val INITIAL_LOAD_SIZE = 60
        const val PROFILE_IMAGE_CACHE_RESOLUTION = 500
    }
}