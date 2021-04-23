package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.service.utils.Validator.validateSearchText

class SearchViewModel: ViewModel() {

    val validatorMsgLiveData: MutableLiveData<Pair<Boolean, Int?>> = MutableLiveData()

    val listCategoryLiveData: MutableLiveData<List<String>> = MutableLiveData()

    fun validate(search: String) {
        validatorMsgLiveData.value = validateSearchText(search)
    }

    fun getListCategories() {
        val items = listOf(
            "Animal",
            "Career",
            "Celebrity",
            "Dev",
            "Explicit",
            "Fashion",
            "Food",
            "History",
            "Money",
            "Movie",
            "Music",
            "Political",
            "Religion",
            "Science",
            "Sport",
            "Travel"
        )//TEMPORÁRIO
        listCategoryLiveData.value = items
    }
}
