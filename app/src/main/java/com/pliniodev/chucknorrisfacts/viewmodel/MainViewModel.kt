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

//    val showError = SingleLiveEvent<String?>()

    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getFactsFromFreeSearch(query: String) {
        viewModelScope.launch {
            repository.getFact(query) { result: FactsResult ->
                when (result) {
                    is FactsResult.Success -> {
                        searchResultLiveData.value = result.successData
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_FACTS, null)
                    }
                    is FactsResult.ApiError -> {
                        when (result.statusCode) {
                            400 -> {
                                viewFlipperLiveData.value =
                                    Pair(VIEW_FLIPPER_ERROR, R.string.facts_error_400)
                            }
                             404 -> {
                                viewFlipperLiveData.value =
                                    Pair(VIEW_FLIPPER_ERROR, R.string.facts_error_404)
                            }
                            500 -> {
                                viewFlipperLiveData.value =
                                    Pair(VIEW_FLIPPER_ERROR, R.string.facts_error_500)
                            }
                        }
                    }
                    is FactsResult.ServerError -> {
                        viewFlipperLiveData.value =
                            Pair(VIEW_FLIPPER_ERROR, R.string.facts_error_server_error)
                    }
                    is FactsResult.ConnectionError -> {
                        viewFlipperLiveData.value =
                            Pair(VIEW_FLIPPER_ERROR, R.string.facts_error_lost_connection)
                    }
                }
            }
        }
    }

    companion object {
        private const val VIEW_FLIPPER_FACTS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }

}


