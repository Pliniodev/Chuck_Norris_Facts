package com.pliniodev.chucknorrisfacts.service.repository

import android.content.Context
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.utils.CheckNetworkConnection.isOnline
import com.pliniodev.chucknorrisfacts.service.utils.CheckNetworkConnection.noNetworkConnectivityError
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class ChuckNorrisRepositoryImpl(
    private val api: ChuckNorrisApi
) : ChuckNorrisRepository {

    private val context: Context by inject(Context::class.java)

    override suspend fun getByFreeQuery(
        query: String,
        factsResult: (result: FactsResult<List<Fact>>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            if (isOnline(context)) {

                val responseByFreeQuery = api.getByFreeQuery(query)

                try {
                    val factDetailsResponse: MutableList<Fact> = mutableListOf()

                    if (responseByFreeQuery.isSuccessful) {

                        responseByFreeQuery.body()?.let { factsResultResponse ->
                            for (result in factsResultResponse.result) {
                                val fact = result.getFactModel()
                                factDetailsResponse.add(fact)
                            }
                        }
                        factsResult(FactsResult.Success(factDetailsResponse))

                    } else {
                        factsResult(FactsResult.ApiError(responseByFreeQuery.code()))
                    }

                } catch (e: Exception) {
                    factsResult(FactsResult.ServerError)
                }
            } else {
                factsResult(noNetworkConnectivityError())
            }
        }
    }

    override suspend fun getByCategory(
        category: String,
        factsResult: (result: FactsResult<List<Fact>>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            if (isOnline(context)) {

                val responseByCategory = api.getByCategory(category)

                try {
                    val factDetailsResponse: MutableList<Fact> = mutableListOf()
                    if (responseByCategory.isSuccessful) {

                        responseByCategory.body()?.let { factsResultResponse ->

                            factDetailsResponse.add(factsResultResponse.getFactModel())

                        }
                        factsResult(FactsResult.Success(factDetailsResponse))

                    } else {
                        factsResult(FactsResult.ApiError(responseByCategory.code()))
                    }

                } catch (e: Exception) {
                    factsResult(FactsResult.ServerError)
                }
            } else {
                factsResult(noNetworkConnectivityError())
            }
        }
    }

    override suspend fun getByRandom(
        factsResult: (result: FactsResult<List<Fact>>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            val responseByRandom = api.getRandom()

            if (isOnline(context)) {
                try {
                    val factDetailsResponse: MutableList<Fact> = mutableListOf()
                    if (responseByRandom.isSuccessful) {

                        responseByRandom.body()?.let { factsResultResponse ->

                            factDetailsResponse.add(factsResultResponse.getFactModel())

                        }
                        factsResult(FactsResult.Success(factDetailsResponse))
                    } else {
                        factsResult(FactsResult.ApiError(responseByRandom.code()))
                    }
                } catch (e: Exception) {
                    factsResult(FactsResult.ServerError)
                }
            } else {
                factsResult(noNetworkConnectivityError())
            }
        }
    }

    override suspend fun getCategoriesList(
        factsResult: (result: FactsResult<List<String>>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            if (isOnline(context)) {

                val responseCategories = api.getCategoriesList()

                try {
                    val categoriesList: MutableList<String> = mutableListOf()

                    if (responseCategories.isSuccessful) {

                        responseCategories.body()?.let { factsResultResponse ->
                            for (result in factsResultResponse) {
                                categoriesList.add(result)
                            }
                        }
                        factsResult(FactsResult.Success(categoriesList))

                    } else {
                        factsResult(FactsResult.ApiError(responseCategories.code()))
                    }

                } catch (e: Exception) {
                    factsResult(FactsResult.ServerError)
                }
            } else {
                factsResult(noNetworkConnectivityError())
            }
        }
    }
}