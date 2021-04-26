package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.constants.Constants
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.utils.onError
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult
import com.pliniodev.chucknorrisfacts.service.utils.Validator.validateSearchText
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: ChuckNorrisRepository
) : ViewModel() {

    val validatorMsgLiveData: MutableLiveData<Pair<Boolean, Int?>> = MutableLiveData()

    val listCategoryLiveData: MutableLiveData<List<String>> = MutableLiveData()

    val errorListMsgLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun validate(search: String) {
        validatorMsgLiveData.value = validateSearchText(search)
    }

    fun getListCategories() {

        viewModelScope.launch {
            repository.getCategoriesList() { result: FactsResult<List<String>> ->
                when (result) {
                    is FactsResult.Success -> {
                        listCategoryLiveData.postValue(result.successData)
                        errorListMsgLiveData.postValue(Pair(Constants.SUCCESS, null))
                    }
                    is FactsResult.ApiError -> {
                        errorListMsgLiveData.postValue(onError(result.statusCode))
                    }
                    is FactsResult.ConnectionError ->
                        errorListMsgLiveData.postValue(Pair(Constants.RESULT_ERROR,
                            R.string.facts_error_lost_connection)
                    )
                    is FactsResult.ServerError ->
                        errorListMsgLiveData.postValue(Pair(Constants.RESULT_ERROR,
                            R.string.facts_error_server_error))
                }
            }
        }
    }
}
