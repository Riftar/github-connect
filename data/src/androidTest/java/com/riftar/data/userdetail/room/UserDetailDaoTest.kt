package com.riftar.data.userdetail.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.riftar.data.common.database.AppDatabase
import com.riftar.data.userdetail.room.dao.UserDetailDao
import com.riftar.data.userdetail.room.entity.UserDetailEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDetailDaoTest {
    private lateinit var userDetailDao: UserDetailDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        userDetailDao = db.getUserDetailDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeUserAndGetUser() = runBlocking {
        val user = UserDetailEntity(
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
            name = "Kerry Ramirez Response",
            twitterUsername = "Dean Underwood"
        )
        userDetailDao.insertAll(user)
        val userByID = userDetailDao.getUserDetailById(1)
        assertEquals(user, userByID)
    }

    @Test
    fun writeUserAndClearUser() = runBlocking {
        val user = UserDetailEntity(
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
            name = "Kerry Ramirez Response",
            twitterUsername = "Dean Underwood"
        )
        userDetailDao.insertAll(user)
        userDetailDao.clearAll()
        val userByID = userDetailDao.getUserDetailById(1)
        assertEquals(null, userByID)
    }

    @Test
    fun transactionInsertAndGetUserDetail() = runBlocking {
        val user = UserDetailEntity(
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
            name = "Kerry Ramirez Response",
            twitterUsername = "Dean Underwood"
        )
        val userByID = userDetailDao.insertAndGetUserDetail(user)
        assertEquals(user, userByID)
    }
}