package com.pliniodev.chucknorrisfacts.di

import com.pliniodev.chucknorrisfacts.viewmodel.MainViewModel
import com.pliniodev.chucknorrisfacts.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    factory {
        //todo MainRepository
    }

    viewModel {
        MainViewModel(
            //todo get()
        )
    }

}

val searchModule = module {
    viewModel {
        SearchViewModel()
    }
}