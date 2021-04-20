package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.service.utils.Validator.validateSearchText

class SearchViewModel: ViewModel() {

    val validatorMsgLiveData: MutableLiveData<Pair<Boolean, Int?>> = MutableLiveData()

    fun validate(search: String) {
        validatorMsgLiveData.value = validateSearchText(search)
    }
}
