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

class UserDetailRepositoryImpl(
    private val api: UserDetailAPI,
    private val listUserDao: ListUserDao,
    private val notesDao: NotesDao,
    private val userDetailDao: UserDetailDao
) :
    UserDetailRepository {
    /***
     * For supporting offline data, I apply this flow of data:
     * 1. Get data from API
     * 2. Save data to local DB
     * 3. Get UserDetailEntity data from local DB
     * 4. Get NotesEntity data with corresponding userId from local DB
     * 5. Insert Notes data into UserDetail
     */
    override suspend fun getUserDetail(userId: Int, userName: String): Result<UserDetail> {
        return try {
            val response = api.getUserDetail(userName)
            val result = response.body()
            if (response.isSuccessful && result?.id !== null) {
                val notes = notesDao.getNotesByUserId(result.id)

                userDetailDao.insertAll(result.toEntity())
                val localData = userDetailDao.getUserDetailById(result.id)
                Result.success(localData.toDomainModel(notes?.notes.orEmpty()))

            } else {
//                throw Exception(response.message())
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            //TODO Handle offline data
//            val notes = notesDao.getNotesByUserId(userId)
//            val localData = userDetailDao.getUserDetailById(userId)
//            Result.success(localData.toDomainModel(notes?.notes.orEmpty()))
            Result.failure(e)
        }
    }

    override suspend fun saveNotes(userId: Int, notes: String): Result<Boolean> {
        // Save notes to the database
        val notesEntity = notesDao.getNotesByUserId(userId)
        if (notesEntity != null) {
            notesDao.updateNotes(notesEntity.copy(notes = notes))
        } else notesDao.insertAll(NotesEntity(userId = userId, notes = notes))

        // Update the hasNotes flag
        val userEntity = listUserDao.getUserById(userId)

        userEntity?.let {
            val rowsUpdated = listUserDao.updateUser(it.copy(hasNotes = true))
            if (rowsUpdated > 0) return Result.success(true)
        }
        return Result.failure(Exception("Failed to save notes"))
    }
}