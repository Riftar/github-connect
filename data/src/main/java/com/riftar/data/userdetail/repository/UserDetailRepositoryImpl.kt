package com.riftar.data.userdetail.repository

import androidx.room.withTransaction
import com.riftar.data.common.database.AppDatabase
import com.riftar.data.common.notes.room.entity.NotesEntity
import com.riftar.data.userdetail.api.UserDetailAPI
import com.riftar.data.userdetail.mapper.toDomainModel
import com.riftar.data.userdetail.mapper.toEntity
import com.riftar.domain.userdetail.model.UserDetail
import com.riftar.domain.userdetail.repository.UserDetailRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

class UserDetailRepositoryImpl(
    private val api: UserDetailAPI,
    private val appDatabase: AppDatabase
) : UserDetailRepository {

    private val listUserDao = appDatabase.getListUserDao()
    private val notesDao = appDatabase.getNotesDao()
    private val userDetailDao = appDatabase.getUserDetailDao()

    override fun getFlowUserDetail(userId: Int, userName: String): Flow<Result<UserDetail>> = flow {
        // 1. Emit old data from database if it exists
        val localData = userDetailDao.getUserDetailById(userId)
        if (localData != null) {
            val notes = notesDao.getNotesByUserId(userId)
            emit(Result.success(localData.toDomainModel(notes?.notes.orEmpty())))
        }

        // 2. Fetch new data from API and update
        try {
            val response = api.getUserDetail(userName)
            val result = response.body()
            if (response.isSuccessful && result?.id != null) {
                val notes = notesDao.getNotesByUserId(result.id)
                val updatedLocalData = userDetailDao.insertAndGetUserDetail(result.toEntity())
                updatedLocalData?.let {
                    emit(Result.success(it.toDomainModel(notes?.notes.orEmpty())))
                } ?: throw (Exception("Failed to insert or retrieve user detail"))
            } else {
                emit(Result.failure(Exception(response.message())))
            }
        } catch (e: Exception) {
            // 3. Emit error to show error immediately, but don't stop the flow
            emit(Result.failure(e))
        }
    }.retryWhen { cause, attempt ->
        // 4. Retry the flow when IOException is thrown and the attempt count is less than 3
        if (cause is IOException && attempt < 3) {
            val delay = 1000 * (attempt + 1)
            delay(delay)
            return@retryWhen true
        } else {
            emit(Result.failure(cause))
            return@retryWhen false
        }
    }.catch { e ->
        emit(Result.failure(e))
    }

    override fun saveNotes(userId: Int, notes: String): Flow<Result<Unit>> = flow {
        // withTransaction ensure all the queries are executed in a single transaction.
        // If any query fails, the entire transaction is rolled back.
        val result = appDatabase.withTransaction {
            try {
                // 1. Update the hasNotes flag if user exist
                val userEntity = listUserDao.getUserById(userId)
                userEntity?.let {
                    listUserDao.updateUser(it.copy(hasNotes = true))
                }

                // 2. Save notes to the database
                val rowsAffected = notesDao.insertAll(
                    NotesEntity(
                        userId = userId,
                        notes = notes
                    )
                )
                // 3. Return result
                if (rowsAffected > 0) Result.success(Unit) else Result.failure(Exception("Failed to save notes"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
        emit(result)
    }.catch { e ->
        // This catch block will handle any exceptions thrown in the flow
        emit(Result.failure(e))
    }
}