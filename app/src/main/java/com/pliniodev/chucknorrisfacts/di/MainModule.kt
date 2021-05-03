package com.pliniodev.chucknorrisfacts.di

import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisApi
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepositoryImpl
import com.pliniodev.chucknorrisfacts.service.retrofit.createApi
import com.pliniodev.chucknorrisfacts.service.retrofit.provideOkHttpClient
import com.pliniodev.chucknorrisfacts.service.retrofit.provideRetrofit
import com.pliniodev.chucknorrisfacts.viewmodel.MainViewModel
import com.pliniodev.chucknorrisfacts.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val remoteModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { createApi<ChuckNorrisApi>(get()) }
}

val repositoryModule = module {
    single<ChuckNorrisRepository> { ChuckNorrisRepositoryImpl(get()) }
}

val uiModule = module {
    viewModel { MainViewModel(repository = get()) }
    viewModel { SearchViewModel(repository = get()) }
}

