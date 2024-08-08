package com.riftar.githubconnect.di.module

import com.riftar.data.base.client.createOkHttp
import com.riftar.data.base.client.createRetrofit
import com.riftar.data.base.client.provideNetworkErrorInterceptor
import com.riftar.data.listuser.api.ListUserAPI
import com.riftar.data.userdetail.api.UserDetailAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single { provideNetworkErrorInterceptor(get()) }
    single {
        createOkHttp(get())
    }
    single {
        createRetrofit(get())
    }
    single { provideListUserAPI(get()) }
    single { provideUserDetailAPI(get()) }
}

fun provideListUserAPI(retrofit: Retrofit): ListUserAPI = retrofit.create(ListUserAPI::class.java)
fun provideUserDetailAPI(retrofit: Retrofit): UserDetailAPI = retrofit.create(UserDetailAPI::class.java)