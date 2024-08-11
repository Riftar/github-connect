package com.riftar.data.listuser.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.riftar.data.common.database.AppDatabase
import com.riftar.data.listuser.room.dao.ListUserDao
import com.riftar.data.listuser.room.entity.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
@SmallTest
class ListUserDaoTest {
    private lateinit var listUserDao: ListUserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        listUserDao = db.getListUserDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeUsersAndGetUsersById() = runBlocking {
        listUserDao.upsertAll(getListUser())
        val user = listUserDao.getUserById(1)
        assertEquals(getListUser().first { it.id == 1 }, user)
    }

    @Test
    fun writeUsersAndClearUsers() = runBlocking {
        listUserDao.upsertAll(getListUser())
        listUserDao.clearAll()
        val userById = listUserDao.getUserById(1)
        assertEquals(null, userById)
    }

    @Test
    fun insertAndUpdateUser() = runBlocking {
        listUserDao.upsertAll(getListUser())
        val user = listUserDao.getUserById(1)
        val rowsAffected = user?.let {
            listUserDao.updateUser(
                it.copy(
                    avatarUrl = "updated avatarUrl",
                    hasNotes = true
                )
            )
        }
        assertEquals(1, rowsAffected)

        val updatedUser = listUserDao.getUserById(1)
        assertEquals(getUpdatedUser(), updatedUser)
    }

    private fun getListUser() = listOf(
        UserEntity(
            id = 1,
            avatarUrl = "avatarUrl",
            htmlUrl = "htmlUrl",
            userName = "phil",
            hasNotes = false
        ),
        UserEntity(
            id = 2,
            avatarUrl = "avatarUrl 2",
            htmlUrl = "htmlUrl 2",
            userName = "luke",
            hasNotes = false
        ),
        UserEntity(
            id = 3,
            avatarUrl = "avatarUrl 3",
            htmlUrl = "htmlUrl 3",
            userName = "phil jr",
            hasNotes = false
        ),
    )

    private fun getUpdatedUser() = UserEntity(
        id = 1,
        avatarUrl = "updated avatarUrl",
        htmlUrl = "htmlUrl",
        userName = "phil",
        hasNotes = true
    )
}