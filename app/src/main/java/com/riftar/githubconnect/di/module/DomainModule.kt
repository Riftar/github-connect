package com.riftar.githubconnect.di.module

import com.riftar.data.listuser.repository.ListUserRepositoryImpl
import com.riftar.domain.listuser.repository.ListUserRepository
import com.riftar.domain.listuser.usecase.GetListUserUseCase
import org.koin.dsl.module

val domainModule = module  {
    single<ListUserRepository> { ListUserRepositoryImpl(get()) }
    single { GetListUserUseCase(get()) }
}