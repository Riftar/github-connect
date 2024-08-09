package com.riftar.data.listuser.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.riftar.data.common.notes.room.dao.NotesDao
import com.riftar.data.listuser.api.ListUserAPI
import com.riftar.data.listuser.mapper.toDomainModel
import com.riftar.data.listuser.pagingsource.ListUserPagingSource
import com.riftar.data.listuser.pagingsource.ListUserPagingSource.Companion.DEFAULT_PAGE_SIZE
import com.riftar.data.listuser.pagingsource.ListUserPagingSource.Companion.INITIAL_LOAD_SIZE
import com.riftar.data.listuser.remotemediator.ListUserRemoteMediator
import com.riftar.data.listuser.room.dao.ListUserDao
import com.riftar.domain.listuser.model.User
import com.riftar.domain.listuser.repository.ListUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListUserRepositoryImpl(
    private val api: ListUserAPI,
    private val listUserDao: ListUserDao,
    private val notesDao: NotesDao
) :
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
            pagingSourceFactory = { listUserDao.pagingSource() },
            remoteMediator = ListUserRemoteMediator(api, listUserDao)
        ).flow.map { pagingData ->
            pagingData.map { userEntity ->
                val notes = notesDao.getNotesByUserId(userEntity.id)
                userEntity.toDomainModel(notes != null)
            }
        }
    }

    override fun getListUser(): Flow<PagingData<User>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { ListUserPagingSource(api, listUserDao) }
        ).flow
    }

    override fun getListUserByQuery(query: String): Flow<PagingData<User>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { listUserDao.getListUserByQuery(query) }
        ).flow.map { pagingData ->
            pagingData.map { userEntity ->
                val notes = notesDao.getNotesByUserId(userEntity.id)
                userEntity.toDomainModel(notes != null)
            }
        }
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = INITIAL_LOAD_SIZE
        )
    }
}