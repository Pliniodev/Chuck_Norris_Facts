package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult
import com.pliniodev.chucknorrisfacts.viewmodel.test_utils.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()//para testes com coroutines

    @Mock
    private lateinit var searchResultLiveDataObserver: Observer<List<Fact>>

    @Mock
    private lateinit var viewFlipperLiveDataObserver: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: MainViewModel

    @ExperimentalCoroutinesApi
    @Test
    fun `Must set searchResultLiveData, when viewModel searchResult, get Success`() {
        //Arrange
        val facts = listOf(
            Fact(
                categories = arrayListOf("food"),
                created_at = "2020-01-05 13:42:18.823766",
                icon_url = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
                id = "MjtEesffSd6AH3Pxbw7_lg",
                updated_at = "2020-01-05 13:42:18.823766",
                url = "https://api.chucknorris.io/jokes/MjtEesffSd6AH3Pxbw7_lg",
                value = "When Chuck Norris played Chopped from Food Network, he finished " +
                        "his food in 1 millisecond, and instantly wins every dish. You " +
                        "didn't see him play because the episode is secret.",
            )
        )
        val resultSuccess = MockRepository(FactsResult.Success(facts))
        viewModel = MainViewModel(resultSuccess)
        viewModel.searchResultLiveData.observeForever(searchResultLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        //act
        viewModel.getFactsFromFreeSearch(query = "food")

        //Assert
        verify(searchResultLiveDataObserver).onChanged(facts)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(1, null))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Must set viewFlipperLiveData, when viewModel searchResult get error 404`() {

        val mockRepository = MockRepository(FactsResult.ApiError(404))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)


        viewModel.getFactsFromFreeSearch(query = "fo")


        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.facts_error_404))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Must set viewFlipperLiveData, when viewModel searchResult get error 400`() {

        val mockRepository = MockRepository(FactsResult.ApiError(400))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)


        viewModel.getFactsFromFreeSearch(query = "fo")


        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.facts_error_400))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Must set viewFlipperLiveData, when viewModel searchResult get error 500`() {

        val mockRepository = MockRepository(FactsResult.ApiError(500))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)


        viewModel.getFactsFromFreeSearch(query = "fo")


        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.facts_error_500))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Must set viewFlipperLiveData, when viewModel searchResult get ServerError`() {

        val mockRepository = MockRepository(FactsResult.ServerError)
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)


        viewModel.getFactsFromFreeSearch(query = "fo")


        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.facts_error_server_error))
    }
}

class MockRepository(private val result: FactsResult) : ChuckNorrisRepository {
    override suspend fun getFact(query: String, resultCallback: (result: FactsResult) -> Unit) {
        resultCallback(result)
    }
}

