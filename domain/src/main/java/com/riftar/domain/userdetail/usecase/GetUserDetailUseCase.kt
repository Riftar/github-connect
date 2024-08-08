package com.riftar.domain.userdetail.usecase

import com.riftar.domain.userdetail.repository.UserDetailRepository

class GetUserDetailUseCase(private val repository: UserDetailRepository) {
    suspend operator fun invoke(userName: String) = repository.getUserDetail(userName)
}