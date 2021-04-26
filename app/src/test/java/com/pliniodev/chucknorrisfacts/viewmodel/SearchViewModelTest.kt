package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult
import com.pliniodev.chucknorrisfacts.test_utils.MockRepository
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var validatorMsgLiveDataObserver: Observer<Pair<Boolean, Int?>>

    private lateinit var viewModel: SearchViewModel

    @Test
    fun `Should set validatorMsgLiveData when viewModel validate stringToValidate isEmpty`() {
        val stringToValidate =""
        val mock = MockRepository(FactsResult.Success(arrayListOf()))
        viewModel = SearchViewModel(mock)
        viewModel.validatorMsgLiveData.observeForever(validatorMsgLiveDataObserver)

        viewModel.validate(stringToValidate)

        verify(validatorMsgLiveDataObserver).onChanged(Pair(false, R.string.you_need_search))
    }

    @Test
    fun `Should set validatorMsgLiveData when viewModel validate stringToValidate smaller than 3`() {
        val stringToValidate = "fo"
        val mock = MockRepository(FactsResult.Success(arrayListOf()))
        viewModel = SearchViewModel(mock)
        viewModel.validatorMsgLiveData.observeForever(validatorMsgLiveDataObserver)

        viewModel.validate(stringToValidate)

        verify(validatorMsgLiveDataObserver).onChanged(Pair(false, R.string.min_search_characters))
    }

    @Test
    fun `Should set validatorMsgLiveData when viewModel validate stringToValidate is Ok`() {
        val stringToValidate = "food"
        val mock = MockRepository(FactsResult.Success(arrayListOf()))
        viewModel = SearchViewModel(mock)
        viewModel.validatorMsgLiveData.observeForever(validatorMsgLiveDataObserver)

        viewModel.validate(stringToValidate)

        verify(validatorMsgLiveDataObserver).onChanged(Pair(true, R.string.yeah_its_a_search))
    }

    @Test
    fun `Should set validatorMsgLiveData when viewModel validate stringToValidate bigger than 120`() {
        val stringToValidate = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Etiam eget ligula eu lectus lobortis condimentum. Aliquam nonummy auctor" +
                " massa. Pellentesque habitant morbi tristique senectus et netus et malesuada " +
                "fames ac turpis egestas. Nulla at risus. Quisque purus magna, auctor et, sagittis" +
                "ac, posuere eu, lectus. Nam mattis, felis ut adipiscing"
        val mock = MockRepository(FactsResult.Success(arrayListOf()))
        viewModel = SearchViewModel(mock)
        viewModel.validatorMsgLiveData.observeForever(validatorMsgLiveDataObserver)

        viewModel.validate(stringToValidate)

        verify(validatorMsgLiveDataObserver).onChanged(Pair(false, R.string.max_search_characters))
    }

    @Test
    fun `Should set validatorMsgLiveData when viewModel validate stringToValidate have symbols`() {
        val stringToValidate = "/~~´[]"

        val mock = MockRepository(FactsResult.Success(arrayListOf()))
        viewModel = SearchViewModel(mock)
        viewModel.validatorMsgLiveData.observeForever(validatorMsgLiveDataObserver)

        viewModel.validate(stringToValidate)

        verify(validatorMsgLiveDataObserver).onChanged(Pair(false, R.string.no_symbols))
    }
}