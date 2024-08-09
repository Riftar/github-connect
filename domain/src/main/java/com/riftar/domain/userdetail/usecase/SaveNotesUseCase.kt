package com.riftar.domain.userdetail.usecase

import com.riftar.domain.userdetail.repository.UserDetailRepository

class SaveNotesUseCase(private val repository: UserDetailRepository) {
    operator fun invoke(userId: Int, notes: String) = repository.saveNotes(userId, notes)
}