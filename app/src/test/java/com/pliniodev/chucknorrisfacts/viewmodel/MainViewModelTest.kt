package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.pliniodev.chucknorrisfacts.data.response.FactDetailsResponse
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
    private lateinit var showErrorObserver: Observer<String?>

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

        //act
        viewModel.getFactsFromFreeSearch(query = "food")

        //Assert
        verify(searchResultLiveDataObserver).onChanged(facts)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Must set showError, when viewModel searchResult get Error`() {

        val mockRepository = MockRepository(FactsResult.Error(404))
        viewModel = MainViewModel(mockRepository)
        viewModel.showError.observeForever(showErrorObserver)


        viewModel.getFactsFromFreeSearch(query = "fo")


        verify(showErrorObserver).onChanged("erro 404")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Must set showError, when viewModel searchResult get ServerError`() {

        val mockRepository = MockRepository(FactsResult.ServerError)
        viewModel = MainViewModel(mockRepository)
        viewModel.showError.observeForever(showErrorObserver)


        viewModel.getFactsFromFreeSearch(query = "fo")


        verify(showErrorObserver).onChanged("erro 500")
    }
}

class MockRepository(private val result: FactsResult) : ChuckNorrisRepository {
    override suspend fun getFact(query: String, resultCallback: (result: FactsResult) -> Unit) {
        resultCallback(result)
    }
}

