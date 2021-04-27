package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.constants.Constants
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult
import com.pliniodev.chucknorrisfacts.service.utils.onError
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ChuckNorrisRepository
) : ViewModel() {

    val searchResultLiveData = MutableLiveData<List<Fact>>()

    val viewFlipperLiveData = MutableLiveData<Pair<Int, Int?>>()

    val connectionErrorLiveData = MutableLiveData<Pair<Int, Boolean>>()

    fun getByFreeSearch(query: String) {
        viewModelScope.launch {
            repository.getByFreeQuery(query) { result: FactsResult<List<Fact>> ->
                onSearchResult(result)
            }
        }
    }
    fun getByRandom() {
        viewModelScope.launch {
            repository.getByRandom() { result: FactsResult<List<Fact>> ->
                onSearchResult(result)
            }
        }
    }
    fun getByCategory(category: String) {
        viewModelScope.launch {
            repository.getByCategory(category) { result: FactsResult<List<Fact>> ->
                onSearchResult(result)
            }
        }
    }

    private fun onSearchResult(result: FactsResult<List<Fact>>) {
        when (result) {
            is FactsResult.Success -> {
                if (result.successData.isEmpty()){
                    viewFlipperLiveData.postValue(Pair(Constants.VIEW_FLIPPER_SEARCH_IS_EMPTY, R.string.error_empty_search))
                } else {
                    searchResultLiveData.postValue(result.successData!!)
                    viewFlipperLiveData.postValue(Pair(Constants.VIEW_FLIPPER_FACTS, null))
                }
            }
            is FactsResult.ApiError -> {
                viewFlipperLiveData.postValue(onError(result.statusCode))
            }
            is FactsResult.ServerError -> {
                viewFlipperLiveData.postValue(Pair(Constants.VIEW_FLIPPER_ERROR,
                    R.string.error_server))
            }
            is FactsResult.ConnectionError -> {
                connectionErrorLiveData.postValue((Pair(R.string.error_lost_connection,
                    true)))
            }
        }
    }




}




