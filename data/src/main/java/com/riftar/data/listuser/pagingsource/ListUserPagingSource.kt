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
            val currentPage = params.key ?: 0
            // TODO get total pages from response
            val totalPages = 99
            val response = api.getListUser(
                since = currentPage * DEFAULT_PAGE_SIZE,
                perPage = DEFAULT_PAGE_SIZE
            )
            if (response.isSuccessful) {
                LoadResult.Page(
                    response.body().orEmpty().map { it.toDomainModel() },
                    prevKey = if (currentPage == 0) null else currentPage - 1,
                    nextKey = if (currentPage < totalPages) currentPage + 1 else null
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
        const val INITIAL_LOAD_SIZE = 20
        const val PROFILE_IMAGE_CACHE_RESOLUTION = 500
    }
}