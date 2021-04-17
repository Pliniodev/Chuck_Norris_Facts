package com.pliniodev.chucknorrisfacts.service.repository

import android.content.Context
import com.pliniodev.chucknorrisfacts.service.model.FactsResultModel
import com.pliniodev.chucknorrisfacts.service.utils.AppResult
import com.pliniodev.chucknorrisfacts.service.utils.CheckNetworkConnection.isOnline
import com.pliniodev.chucknorrisfacts.service.utils.Utils.handleApiError
import com.pliniodev.chucknorrisfacts.service.utils.Utils.handleSuccess
import com.pliniodev.chucknorrisfacts.service.utils.noNetworkConnectivityError
import java.lang.Exception


class ChuckNorrisRepositoryImpl(
    private val api: ChuckNorrisApi,
    private val context: Context,
) : ChuckNorrisRepository {

    override suspend fun getFreeQuery(query: String): AppResult<FactsResultModel> {
        if (isOnline(context)) {
            return try {
                val response = api.getFreeQuery(query)
                if (response.isSuccessful) {
                    handleSuccess(response)
                } else {
                    handleApiError(response)
                }
            } catch (e: Exception) {
                AppResult.Error(e)
            }
        } else {
            return context.noNetworkConnectivityError()
        }
    }
}