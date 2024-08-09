package com.riftar.data.userdetail.repository

import com.riftar.data.common.notes.room.dao.NotesDao
import com.riftar.data.common.notes.room.entity.NotesEntity
import com.riftar.data.listuser.room.dao.ListUserDao
import com.riftar.data.userdetail.api.UserDetailAPI
import com.riftar.data.userdetail.mapper.toDomainModel
import com.riftar.data.userdetail.mapper.toEntity
import com.riftar.data.userdetail.room.dao.UserDetailDao
import com.riftar.domain.userdetail.model.UserDetail
import com.riftar.domain.userdetail.repository.UserDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UserDetailRepositoryImpl(
    private val api: UserDetailAPI,
    private val listUserDao: ListUserDao,
    private val notesDao: NotesDao,
    private val userDetailDao: UserDetailDao
) :
    UserDetailRepository {

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
                userDetailDao.insertAll(result.toEntity())

                val updatedLocalData = userDetailDao.getUserDetailById(result.id)
                emit(Result.success(updatedLocalData.toDomainModel(notes?.notes.orEmpty())))
            } else {
                emit(Result.failure(Exception(response.message())))
            }
        } catch (e: Exception) {
            // 3. Emit error, but don't stop the flow
            emit(Result.failure(e))
        }
    }.catch { e ->
        // This catch block will handle any exceptions thrown in the flow
        emit(Result.failure(e))
    }

    override fun saveNotes(userId: Int, notes: String): Flow<Result<Unit>> = flow {

        // 1. Update the hasNotes flag if user exist
        val userEntity = listUserDao.getUserById(userId)
        userEntity?.let {
            listUserDao.updateUser(it.copy(hasNotes = true))
        }
        // 2. Save notes to the database
        val notesEntity = notesDao.getNotesByUserId(userId)
        val rowsAffected = if (notesEntity != null) {
            notesDao.updateNotes(notesEntity.copy(notes = notes)).toLong()
        } else notesDao.insertAll(NotesEntity(userId = userId, notes = notes))

        // 3. Emit Result
        if (rowsAffected > 0) emit(Result.success(Unit)) else emit(Result.failure(Exception("Failed to save notes")))
    }.catch { e ->
        // This catch block will handle any exceptions thrown in the flow
        emit(Result.failure(e))
    }
}