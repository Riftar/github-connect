package com.riftar.domain.listuser.usecase

import com.riftar.domain.listuser.repository.ListUserRepository

class GetListUserUseCase(private val repository: ListUserRepository) {
    operator fun invoke() = repository.getListUserRemoteMediator()
}