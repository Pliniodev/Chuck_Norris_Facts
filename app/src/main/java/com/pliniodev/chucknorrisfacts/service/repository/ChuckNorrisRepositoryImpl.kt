package com.pliniodev.chucknorrisfacts.service.repository

import android.content.Context
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.retrofit.RetrofitClient
import com.pliniodev.chucknorrisfacts.service.utils.CheckNetworkConnection.isOnline
import com.pliniodev.chucknorrisfacts.service.utils.CheckNetworkConnection.noNetworkConnectivityError
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult

class ChuckNorrisRepositoryImpl(
    private val retrofitClient: RetrofitClient,
    private val context: Context,
) : ChuckNorrisRepository {

    override suspend fun getFact(query: String, resultCallback: (result: FactsResult) -> Unit) {

        if (isOnline(context)) {
            val response = retrofitClient.retrofitService.getFreeQuery(query)

            try {
                val factDetailsResponse: MutableList<Fact> = mutableListOf()
                if (response.isSuccessful) {

                    response.body()?.let { factsResultResponse ->
                        for (result in factsResultResponse.result) {
                            val fact = result.getFactModel()
                            factDetailsResponse.add(fact)
                        }
                    }
                    resultCallback(FactsResult.Success(factDetailsResponse))

                } else {
                    resultCallback(FactsResult.ApiError(response.code()))
                }

            } catch (e: Exception) {
                resultCallback(FactsResult.ServerError)
            }
        } else {
            resultCallback(noNetworkConnectivityError())
        }
    }
}