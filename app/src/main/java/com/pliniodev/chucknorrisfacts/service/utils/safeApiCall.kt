package com.pliniodev.chucknorrisfacts.service.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**Retorna o FactsResult de acordo com o response da Api
 * apiCall = pede a função estendida(extension) na função suspensa que é feita na api(ChuckNorrisApi)**/


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T

): FactsResult<T> {
    return withContext(dispatcher) {
        try {
            FactsResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> FactsResult.ConnectionError
                is HttpException -> {
                    val code = throwable.code()
                    FactsResult.ApiError(code)
                }
                else -> {
                    FactsResult.ServerError
                }
            }
        }
    }
}

