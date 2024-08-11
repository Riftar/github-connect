package com.riftar.data.userdetail.repository

import com.riftar.data.common.database.AppDatabase
import com.riftar.data.common.notes.room.dao.NotesDao
import com.riftar.data.common.notes.room.entity.NotesEntity
import com.riftar.data.listuser.room.dao.ListUserDao
import com.riftar.data.listuser.room.entity.UserEntity
import com.riftar.data.userdetail.api.UserDetailAPI
import com.riftar.data.userdetail.mapper.toEntity
import com.riftar.data.userdetail.model.UserDetailResponse
import com.riftar.data.userdetail.room.dao.UserDetailDao
import com.riftar.data.userdetail.room.entity.UserDetailEntity
import com.riftar.domain.userdetail.model.UserDetail
import com.riftar.domain.userdetail.repository.UserDetailRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class UserDetailRepositoryTest {

    @Mock
    private lateinit var api: UserDetailAPI

    @Mock
    private lateinit var appDatabase: AppDatabase

    @Mock
    private lateinit var listUserDao: ListUserDao

    @Mock
    private lateinit var notesDao: NotesDao

    @Mock
    private lateinit var userDetailDao: UserDetailDao

    private lateinit var repository: UserDetailRepository

    @Before
    fun setup() {
        `when`(appDatabase.getListUserDao()).thenReturn(listUserDao)
        `when`(appDatabase.getNotesDao()).thenReturn(notesDao)
        `when`(appDatabase.getUserDetailDao()).thenReturn(userDetailDao)

        repository = UserDetailRepositoryImpl(api, appDatabase)
    }

    @Test
    fun `getFlowUserDetail fetch API success then return domain model`() = runTest {
        val userId = 1
        val userName = "Kerry Ramirez"
        val userDetailResponse = mockUserDetailResponse
        val localUserDetail = mockUserDetailEntity.copy(name = "Kerry Ramirez Response")
        val notes = mockNotes
        val domainUserDetail = getDomainUserDetail("Kerry Ramirez Response")

        `when`(api.getUserDetail(userName)).thenReturn(
            Response.success(200, userDetailResponse)
        )
        `when`(notesDao.getNotesByUserId(userId)).thenReturn(notes)
        `when`(userDetailDao.insertAndGetUserDetail(userDetailResponse.toEntity())).thenReturn(
            localUserDetail
        )

        val results = repository.getFlowUserDetail(userId, userName).toList()
        assertEquals(domainUserDetail, results.last().getOrNull())
    }

    @Test
    fun `getFlowUserDetail fetch API fail then return from local`() = runTest {
        val userId = 1
        val userName = "Kerry Ramirez"
        val localUserDetail = mockUserDetailEntity
        val notes = mockNotes
        val domainUserDetail = getDomainUserDetail("Kerry Ramirez Local")

        `when`(userDetailDao.getUserDetailById(userId)).thenReturn(localUserDetail)
        `when`(notesDao.getNotesByUserId(userId)).thenReturn(notes)
        `when`(api.getUserDetail(userName)).thenReturn(
            Response.error(404, "".toResponseBody(null))
        )

        val results = repository.getFlowUserDetail(userId, userName).toList()
        assertEquals(domainUserDetail, results[0].getOrNull())
        // error thrown
        assert(results.last().isFailure)
    }


    /**
     * Still error. can't mock db.withTransaction
     * Cannot invoke "java.util.concurrent.Executor.execute(java.lang.Runnable)" because the return
     * value of "androidx.room.RoomDatabase.getTransactionExecutor()" is null)
     */
//    @Test
//    fun `saveNotes success`() = runTest {
//        val userId = 1
//        val localUserDetail = mockUserEntity
//        val noteString = "notes"
//        val notes = mockNotes
//
//        `when`(listUserDao.getUserById(userId)).thenReturn(localUserDetail)
//        `when`(notesDao.insertAll(notes)).thenReturn(1)
//
//        val results = repository.saveNotes(userId, noteString).toList()
//        assertEquals(Unit, results.last())
//    }
//
//    @Test
//    fun `saveNotes failed`() = runTest {
//        val userId = 1
//        val noteString = "notes"
//        val notes = mockNotes
//
//        `when`(listUserDao.getUserById(userId)).thenReturn(null)
//        `when`(notesDao.insertAll(notes)).thenReturn(-1)
//
//        val results = repository.saveNotes(userId, noteString).toList()
//        assert(results[0].isFailure)
//    }


    private val mockNotes = NotesEntity(userId = 1, notes = "notes")

    private val mockUserEntity = UserEntity(
        id = 1,
        avatarUrl = "http://www.bing.com/search?q=natoque",
        htmlUrl = "http://www.bing.com/search?q=natoque",
        userName = "Dominic Day",
        hasNotes = false
    )

    private val mockUserDetailResponse = UserDetailResponse(
        id = 1,
        avatarUrl = "http://www.bing.com/search?q=natoque",
        bio = "non",
        blog = "persecuti",
        company = "iriure",
        email = "lakeisha.mooney@example.com",
        followers = 1464,
        following = 4837,
        location = "labores",
        login = "Dominic Day",
        name = "Kerry Ramirez Response",
        twitterUsername = "Dean Underwood"
    )
    private val mockUserDetailEntity = UserDetailEntity(
        id = 1,
        avatarUrl = "http://www.bing.com/search?q=natoque",
        bio = "non",
        blog = "persecuti",
        company = "iriure",
        email = "lakeisha.mooney@example.com",
        followers = 1464,
        following = 4837,
        location = "labores",
        userName = "Dominic Day",
        name = "Kerry Ramirez Local",
        twitterUsername = "Dean Underwood"
    )

    private fun getDomainUserDetail(name: String) = UserDetail(
        id = 1,
        avatarUrl = "http://www.bing.com/search?q=natoque",
        bio = "non",
        blog = "persecuti",
        company = "iriure",
        email = "lakeisha.mooney@example.com",
        followers = 1464,
        following = 4837,
        location = "labores",
        userName = "Dominic Day",
        name = name,
        twitterUsername = "Dean Underwood",
        notes = "notes"
    )
}