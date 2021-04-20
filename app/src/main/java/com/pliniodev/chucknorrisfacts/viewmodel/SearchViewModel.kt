package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pliniodev.chucknorrisfacts.R

class SearchViewModel: ViewModel() {

    val validatorMsgLiveData: MutableLiveData<Pair<Boolean, Int?>> = MutableLiveData()

    fun validate(s: Int){
        when {
            s == 0 -> {
                validatorMsgLiveData.value = Pair(false, R.string.you_need_search)
            }
            s < 3 -> {
                validatorMsgLiveData.value = Pair(false, R.string.min_search_characters)
            }
            s in 3..119 -> {
                validatorMsgLiveData.value = Pair(true, R.string.yeah_its_a_search)
            }
            s > 120 -> {
                validatorMsgLiveData.value = Pair(false, R.string.max_search_characters)
            }

            else ->  validatorMsgLiveData.value = Pair(false, null)
        }
    }
}
