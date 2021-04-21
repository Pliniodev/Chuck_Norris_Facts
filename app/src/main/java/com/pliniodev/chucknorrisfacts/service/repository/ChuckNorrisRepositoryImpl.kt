package com.pliniodev.chucknorrisfacts.service.repository

import android.content.Context
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.utils.CheckNetworkConnection.isOnline
import com.pliniodev.chucknorrisfacts.service.utils.CheckNetworkConnection.noNetworkConnectivityError
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult

class ChuckNorrisRepositoryImpl(
    private val context: Context,
    private val api: ChuckNorrisApi
) : ChuckNorrisRepository {

    override suspend fun getByFreeQuery(
        query: String,
        factsResult: (result: FactsResult) -> Unit
    ) {
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

    override suspend fun getByCategory(
        category: String,
        factsResult: (result: FactsResult) -> Unit
    ) {
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

    override suspend fun getByRandom(
        factsResult: (result: FactsResult) -> Unit
    ) {
        val responseByRandom = api.getRandom()

        if (isOnline(context)) {
            try {
                val factDetailsResponse: MutableList<Fact> = mutableListOf()
                if (responseByRandom.isSuccessful) {

                    responseByRandom.body()?.let { factsResultResponse ->

                        factDetailsResponse.add(factsResultResponse.getFactModel())

                    }
                    factsResult(FactsResult.Success(factDetailsResponse))
                }
            } catch (t: Throwable) {
                factsResult(FactsResult.ApiError(responseByRandom.code()))
            }
        } else {
            factsResult(noNetworkConnectivityError())
        }
    }
}