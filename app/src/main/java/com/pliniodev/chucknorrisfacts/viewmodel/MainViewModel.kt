package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.constants.Constants
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult.*
import com.pliniodev.chucknorrisfacts.service.utils.onApiError
import kotlinx.coroutines.launch

/**
 * Aqui foram feitas correções para melhorar a legibilidade do código.
 */

class MainViewModel(
    private val repository: ChuckNorrisRepository
) : ViewModel() {

    val searchResultLiveData = MutableLiveData<List<Fact>>()

    val viewFlipperLiveData = MutableLiveData<Pair<Int, Int?>>()

    val connectionErrorLiveData = MutableLiveData<Pair<Int, Boolean>>()

    fun getByFreeSearch(query: String) {
        viewModelScope.launch {
            when (val result = repository.getByFreeQuery(query)) {
                is Success -> showSuccess(result)
                is ApiError -> showApiError(result)
                is ConnectionError -> showConnectionError()
                is ServerError -> showServerError()
            }
        }
    }

    fun getByCategory(category: String) {
        viewModelScope.launch {
            when (val result = repository.getByCategory(category)) {
                is Success -> showSuccess(result)
                is ApiError -> showApiError(result)
                is ConnectionError -> showConnectionError()
                is ServerError -> showServerError()
            }
        }
    }

    fun getByRandom() {
        viewModelScope.launch {
            when (val result = repository.getByRandom()) {
                is Success -> showSuccess(result)
                is ApiError -> showApiError(result)
                is ConnectionError -> showConnectionError()
                is ServerError -> showServerError()
            }
        }
    }

    private fun showSuccess(result: Success<List<Fact>>) {
        if (result.successData.isEmpty()) {
            viewFlipperLiveData.postValue(
                Pair(
                    Constants.VIEW_FLIPPER_SEARCH_IS_EMPTY,
                    R.string.error_empty_search
                )
            )
        } else {
            searchResultLiveData.postValue(result.successData!!)
            viewFlipperLiveData.postValue(Pair(Constants.VIEW_FLIPPER_FACTS, null))
        }
    }

    private fun showApiError(result: ApiError) {
        viewFlipperLiveData.postValue(onApiError(result.statusCode))
    }

    private fun showConnectionError() {
        connectionErrorLiveData.postValue(
            (Pair(
                R.string.error_lost_connection,
                true
            ))
        )
    }

    private fun showServerError() {
        viewFlipperLiveData.postValue(
            Pair(
                Constants.VIEW_FLIPPER_ERROR,
                R.string.error_server
            )
        )
    }
}


