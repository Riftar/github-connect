package com.riftar.data.common.notes.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.riftar.data.common.notes.room.entity.NotesEntity.Companion.NOTES_TABLE

@Entity(tableName = NOTES_TABLE)
data class NotesEntity(
    @PrimaryKey
    val userId: Int,
    val notes: String?,
) {
    companion object {
        const val NOTES_TABLE = "user_notes"
    }
}