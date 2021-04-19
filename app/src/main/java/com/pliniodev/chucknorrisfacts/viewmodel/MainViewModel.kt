package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult
import com.pliniodev.chucknorrisfacts.service.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ChuckNorrisRepository
) : ViewModel() {

    val searchResultLiveData = MutableLiveData<List<Fact>>()

    val showError = SingleLiveEvent<String?>()

    fun getFactsFromFreeSearch(query: String) {
        viewModelScope.launch {
            repository.getFact(query) { result: FactsResult ->
                when (result) {
                    is FactsResult.Success -> {
                        searchResultLiveData.value = result.successData
                        showError.value = null
                    }
                    is FactsResult.Error -> {
                        if (result.statusCode == 404) {
                            showError.value = "erro 404"
                        }
                    }
                    is FactsResult.ServerError -> {
                        showError.value = "erro 500"
                    }
                    is FactsResult.ConnectionError -> {
                        showError.value = "Conection Lost"
                    }
                }
            }
        }
    }
}


