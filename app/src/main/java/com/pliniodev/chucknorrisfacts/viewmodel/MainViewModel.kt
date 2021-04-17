package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pliniodev.chucknorrisfacts.service.model.FactsResultModel
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.utils.AppResult
import com.pliniodev.chucknorrisfacts.service.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ChuckNorrisRepository
    ): ViewModel() {

    private val query = "food"//temporário

    val searchResult = MutableLiveData<FactsResultModel>()
    val showError = SingleLiveEvent<String?>()

    fun getFreeSearch() {
        //viewModelScope só é executado quando o viewModel está ativo
        viewModelScope.launch {

            when (val result =  repository.getFreeQuery(query)) {
                is AppResult.Success -> {
                    searchResult.value = result.successData!!
                    showError.value = null
                }
                is AppResult.Error -> showError.value = result.exception.message
            }
        }
    }
}


