package com.riftar.githubconnect.di.module

import com.riftar.listuser.ListUserViewModel
import com.riftar.userdetail.UserDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {

    viewModel { ListUserViewModel(get()) }
    viewModel { UserDetailViewModel(get()) }
}