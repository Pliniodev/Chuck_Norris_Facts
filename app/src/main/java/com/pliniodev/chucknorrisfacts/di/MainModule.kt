package com.pliniodev.chucknorrisfacts.di

import android.content.Context
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisApi
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepositoryImpl
import com.pliniodev.chucknorrisfacts.service.retrofit.RetrofitClient
import com.pliniodev.chucknorrisfacts.viewmodel.MainViewModel
import com.pliniodev.chucknorrisfacts.viewmodel.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


val mainModule = module {

    viewModel {
        MainViewModel(
            repository = get()
        )
    }
}

val networkModule = module {

    single { RetrofitClient().provideHttpClient() }

    single {
        val baseURL = androidContext().getString(R.string.BASE_URL)
        RetrofitClient().provideRetrofit(
            client = get(),
            baseUrl = baseURL)
    }
}

val apiModule = module {
    fun provideChuckNorrisApi(retrofit: Retrofit): ChuckNorrisApi {
        return retrofit.create(ChuckNorrisApi::class.java)
    }
    single { provideChuckNorrisApi(retrofit = get()) }
}

val repositoryModule = module {

    fun provideChuckNorrisRepository(api: ChuckNorrisApi, context: Context): ChuckNorrisRepository {
        return ChuckNorrisRepositoryImpl(api, context)
    }

    single { provideChuckNorrisRepository(api = get(), context = androidContext()) }
}

val searchModule = module {
    viewModel {
        SearchViewModel()
    }
}

