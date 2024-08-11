package com.riftar.data.common.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.riftar.data.common.database.AppDatabase
import com.riftar.data.common.notes.room.dao.NotesDao
import com.riftar.data.common.notes.room.entity.NotesEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class NotesDaoTest {
    private lateinit var notesDao: NotesDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        notesDao = db.getNotesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeNotesAndGetNotes() = runBlocking {
        val notes = NotesEntity(userId = 1, notes = "my notes")
        notesDao.insertAll(notes)
        val notesByUserId = notesDao.getNotesByUserId(1)
        assertEquals(notes, notesByUserId)
    }

    @Test
    fun writeNotesAndClearNotes() = runBlocking {
        val notes = NotesEntity(userId = 1, notes = "my notes")
        notesDao.insertAll(notes)
        notesDao.clearAll()
        val notesByUserId = notesDao.getNotesByUserId(1)
        assertEquals(null, notesByUserId)
    }

    @Test
    fun insertAndUpdateNotes() = runBlocking {
        val notes = NotesEntity(userId = 1, notes = "my notes")
        notesDao.insertAll(notes)
        val updatedNotes = NotesEntity(userId = 1, notes = "updated notes")
        notesDao.updateNotes(updatedNotes)
        val notesByUserId = notesDao.getNotesByUserId(1)
        assertEquals(updatedNotes, notesByUserId)
    }
}