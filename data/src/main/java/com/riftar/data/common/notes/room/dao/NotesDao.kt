package com.riftar.data.common.notes.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.riftar.data.common.notes.room.entity.NotesEntity
import com.riftar.data.common.notes.room.entity.NotesEntity.Companion.NOTES_TABLE

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notes: NotesEntity): Long

    @Update
    suspend fun updateNotes(notes: NotesEntity): Int // Returns the number of rows updated

    @Query("DELETE FROM $NOTES_TABLE")
    suspend fun clearAll()

    @Query("SELECT * FROM $NOTES_TABLE WHERE userId = :userId")
    suspend fun getNotesByUserId(userId: Int): NotesEntity?

}