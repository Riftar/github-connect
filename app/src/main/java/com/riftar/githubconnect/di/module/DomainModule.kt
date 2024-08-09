package com.riftar.githubconnect.di.module

import com.riftar.data.listuser.repository.ListUserRepositoryImpl
import com.riftar.data.userdetail.repository.UserDetailRepositoryImpl
import com.riftar.domain.listuser.repository.ListUserRepository
import com.riftar.domain.listuser.usecase.GetListUserUseCase
import com.riftar.domain.userdetail.repository.UserDetailRepository
import com.riftar.domain.userdetail.usecase.GetUserDetailUseCase
import com.riftar.domain.userdetail.usecase.SaveNotesUseCase
import org.koin.dsl.module

val domainModule = module  {
    single<UserDetailRepository> { UserDetailRepositoryImpl(get(), get(), get(), get()) }
    single<ListUserRepository> { ListUserRepositoryImpl(get(), get(), get()) }
    single { GetListUserUseCase(get()) }
    single { GetUserDetailUseCase(get()) }
    single { SaveNotesUseCase(get()) }
}