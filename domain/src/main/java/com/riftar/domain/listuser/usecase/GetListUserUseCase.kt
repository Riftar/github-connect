package com.riftar.domain.listuser.usecase

import com.riftar.domain.listuser.repository.ListUserRepository

class GetListUserUseCase(private val repository: ListUserRepository) {
    operator fun invoke(query: String? = null) = if (!query.isNullOrEmpty()) {
        repository.getListUserByQuery(query)
    } else {
        repository.getListUserRemoteMediator()
    }
}