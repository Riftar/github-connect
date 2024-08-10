package com.riftar.data.listuser.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.riftar.data.common.database.AppDatabase
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

class ListUserRepositoryImpl(
    private val api: ListUserAPI,
    private val appDatabase: AppDatabase
) : ListUserRepository {
    private val listUserDao: ListUserDao = appDatabase.getListUserDao()
    private val notesDao: NotesDao = appDatabase.getNotesDao()

    /***
     * This implementation is still contains bug.
     * The list will load data from internet even though there is a data already on the DB.
     * Need to fix it on the RemoteMediator level.
     */
    @OptIn(ExperimentalPagingApi::class)
    override fun getListUserRemoteMediator(): Flow<PagingData<User>> {
        // 1. Fetch data through remote mediator and save to database
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { listUserDao.pagingSource() },
            remoteMediator = ListUserRemoteMediator(api, appDatabase)
        ).flow.map { pagingData ->
            // 2. Map the data to domain model, add notes flag if user has notes
            pagingData.map { userEntity ->
                val notes = notesDao.getNotesByUserId(userEntity.id)
                userEntity.toDomainModel(notes != null)
            }
        }.retryWhen { cause, attempt ->
            // 3. Retry the flow when IOException is thrown and the attempt count is less than 3
            if (cause is IOException && attempt < 3) {
                val delay = 1000 * (attempt + 1)
                delay(delay)
                return@retryWhen true
            } else {
                emit(PagingData.empty())
                return@retryWhen false
            }
        }.catch { e ->
            emit(PagingData.empty())
        }
    }

    // TODO delete
    override fun getListUser(): Flow<PagingData<User>> {
        // 1. Fetch data through paging source and save to database
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { ListUserPagingSource(api, listUserDao) }
        ).flow.map { pagingData ->
            // 2. Add notes flag if user has notes
            pagingData.map { user ->
                val notes = notesDao.getNotesByUserId(user.id)
                user.copy(hasNotes = notes != null)
            }
        }.retryWhen { cause, attempt ->
            // 3. Retry the flow when IOException is thrown and the attempt count is less than 3
            if (cause is IOException && attempt < 3) {
                val delay = 1000 * (attempt + 1)
                delay(delay)
                return@retryWhen true
            } else {
                emit(PagingData.empty())
                return@retryWhen false
            }
        }.catch { e ->
            emit(PagingData.empty())
        }
    }

    override fun getListUserByQuery(query: String): Flow<PagingData<User>> {
        // 1. Fetch from database based on query
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { listUserDao.getListUserByQuery(query) }
        ).flow.map { pagingData ->
            // 2. Map the data to domain model, add notes flag if user has notes
            pagingData.map { userEntity ->
                val notes = notesDao.getNotesByUserId(userEntity.id)
                userEntity.toDomainModel(notes != null)
            }
        }.retryWhen { cause, attempt ->
            // 3. Retry the flow when IOException is thrown and the attempt count is less than 3
            if (cause is IOException && attempt < 3) {
                val delay = 1000 * (attempt + 1)
                delay(delay)
                return@retryWhen true
            } else {
                emit(PagingData.empty())
                return@retryWhen false
            }
        }.catch { e ->
            emit(PagingData.empty())
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