package com.pliniodev.chucknorrisfacts.di

import android.content.Context
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepositoryImpl
import com.pliniodev.chucknorrisfacts.service.retrofit.RetrofitClient
import com.pliniodev.chucknorrisfacts.viewmodel.MainViewModel
import com.pliniodev.chucknorrisfacts.viewmodel.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainModule = module {

    viewModel {
        MainViewModel(
            repository = get()
        )
    }
}

val networkModule = module {

    single { RetrofitClient.retrofitService }

}


val repositoryModule = module {

    fun provideChuckNorrisRepository(
        service: RetrofitClient,
        context: Context
    ): ChuckNorrisRepository {
        return ChuckNorrisRepositoryImpl(service, context)
    }

    single { provideChuckNorrisRepository(service = RetrofitClient, context = androidContext()) }
}

val searchModule = module {
    viewModel {
        SearchViewModel()
    }
}

