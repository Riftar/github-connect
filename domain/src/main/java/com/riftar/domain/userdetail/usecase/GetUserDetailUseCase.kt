package com.riftar.domain.userdetail.usecase

import com.riftar.domain.userdetail.repository.UserDetailRepository

class GetUserDetailUseCase(private val repository: UserDetailRepository) {
    operator fun invoke(userId: Int, userName: String) = repository.getFlowUserDetail(userId, userName)
}