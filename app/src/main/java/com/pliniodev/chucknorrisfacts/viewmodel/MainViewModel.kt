package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ChuckNorrisRepository
) : ViewModel() {

    val searchResultLiveData = MutableLiveData<List<Fact>>()

    val viewFlipperLiveData = MutableLiveData<Pair<Int, Int?>>()

    fun getByFreeSearch(query: String) {
        viewModelScope.launch {
            repository.getByFreeQuery(query) { result: FactsResult ->
                onSearchResult(result)
            }
        }
    }
    fun getByRandom() {
        viewModelScope.launch {
            repository.getByRandom() { result: FactsResult ->
                onSearchResult(result)
            }
        }
    }
    fun getByCategory(category: String) {
        viewModelScope.launch {
            repository.getByCategory(category) { result: FactsResult ->
                onSearchResult(result)
            }
        }
    }

    private fun onSearchResult(result: FactsResult) {
        when (result) {
            is FactsResult.Success -> {
                searchResultLiveData.postValue(result.successData)
                viewFlipperLiveData.postValue(Pair(VIEW_FLIPPER_FACTS, null))
            }
            is FactsResult.ApiError -> {
                viewFlipperLiveData.postValue(onApiError(result.statusCode))
            }
            is FactsResult.ServerError -> {
                viewFlipperLiveData.postValue(
                    Pair(VIEW_FLIPPER_ERROR, R.string.facts_error_server_error))

            }
            is FactsResult.ConnectionError -> {
                viewFlipperLiveData.postValue(
                    Pair( VIEW_FLIPPER_ERROR,
                    R.string.facts_error_lost_connection)
                )
            }
        }
    }

    private fun onApiError(result: Int): Pair<Int, Int> {
        return when (result) {
            400 -> {
                Pair(VIEW_FLIPPER_ERROR, R.string.facts_error_400)
            }
            404 -> {
                Pair(VIEW_FLIPPER_ERROR, R.string.facts_error_404)
            }
            else -> {
                Pair(VIEW_FLIPPER_ERROR, R.string.facts_error_500)
            }
        }
    }




    companion object{
        private const val VIEW_FLIPPER_FACTS = 2
        private const val VIEW_FLIPPER_ERROR = 3
    }

}




