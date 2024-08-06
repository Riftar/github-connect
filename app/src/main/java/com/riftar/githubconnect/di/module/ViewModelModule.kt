package com.riftar.githubconnect.di.module

import com.riftar.listuser.ListUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {

    viewModel { ListUserViewModel(get()) }
}