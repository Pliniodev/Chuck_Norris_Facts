package com.pliniodev.chucknorrisfacts.service.repository

import com.pliniodev.chucknorrisfacts.service.extension.toCategoriesList
import com.pliniodev.chucknorrisfacts.service.extension.toFact
import com.pliniodev.chucknorrisfacts.service.extension.toFactsList
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult
import com.pliniodev.chucknorrisfacts.service.utils.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO

/**
 * Correções feitas
 *  - Não é mais necessário verificar o se o usuário está online aqui, essa possibilidade agora é
 *  capturada através do tratamento feito na classe safeApiCall.
 *  - Toda a conversão da Response recebida aqui agora é feita nas funções estendidas.
 *  - O Try/catch da realização da call agora é feito dentro da função safeApiCall.
 *  - A função safeApiCall retorna FactsResult e é executada na thread IO.
 */

class ChuckNorrisRepositoryImpl(
    private val api: ChuckNorrisApi,
    private val dispatcher: CoroutineDispatcher = IO
) : ChuckNorrisRepository {

    override suspend fun getByFreeQuery(query: String): FactsResult<List<Fact>> {
        return safeApiCall(dispatcher) { api.getByFreeQuery(query).toFactsList() }
    }

    override suspend fun getByCategory(category: String): FactsResult<List<Fact>> {
        return safeApiCall(dispatcher) { api.getByCategory(category).toFact() }
    }

    override suspend fun getByRandom(): FactsResult<List<Fact>> {
        return safeApiCall(dispatcher) { api.getRandom().toFact() }
    }

    override suspend fun getCategoriesList(): FactsResult<List<String>> {
        return safeApiCall(dispatcher) { api.getCategoriesList().toCategoriesList() }
    }


//    override suspend fun getByFreeQuery(
//        query: String,
//        factsResult: (result: FactsResult<List<Fact>>) -> Unit
//    ) {
//        CoroutineScope(IO).launch {
//            if (isOnline(context)) {
//
//                val responseByFreeQuery = api.getByFreeQuery(query)
//
//                try {
//                    val factDetailsResponse: MutableList<Fact> = mutableListOf()
//
//                    if (responseByFreeQuery.isSuccessful) {
//
//                        responseByFreeQuery.body()?.let { factsResultResponse ->
//                            for (result in factsResultResponse.result) {
//                                val fact = result.getFactModel()
//                                factDetailsResponse.add(fact)
//                            }
//                        }
//                        factsResult(FactsResult.Success(factDetailsResponse))
//
//                    } else {
//                        factsResult(FactsResult.ApiError(responseByFreeQuery.code()))
//                    }
//
//                } catch (e: Exception) {
//                    factsResult(FactsResult.ServerError)
//                }
//            } else {
//                factsResult(noNetworkConnectivityError())
//            }
//        }
//    }
//
//    override suspend fun getByCategory(
//        category: String,
//        factsResult: (result: FactsResult<List<Fact>>) -> Unit
//    ) {
//        CoroutineScope(IO).launch {
//            if (isOnline(context)) {
//
//                val responseByCategory = api.getByCategory(category)
//
//                try {
//                    val factDetailsResponse: MutableList<Fact> = mutableListOf()
//                    if (responseByCategory.isSuccessful) {
//
//                        responseByCategory.body()?.let { factsResultResponse ->
//
//                            factDetailsResponse.add(factsResultResponse.getFactModel())
//
//                        }
//                        factsResult(FactsResult.Success(factDetailsResponse))
//
//                    } else {
//                        factsResult(FactsResult.ApiError(responseByCategory.code()))
//                    }
//
//                } catch (e: Exception) {
//                    factsResult(FactsResult.ServerError)
//                }
//            } else {
//                factsResult(noNetworkConnectivityError())
//            }
//        }
//    }
//
//    override suspend fun getByRandom(
//        factsResult: (result: FactsResult<List<Fact>>) -> Unit
//    ) {
//        CoroutineScope(IO).launch {
//            val responseByRandom = api.getRandom()
//
//            if (isOnline(context)) {
//                try {
//                    val factDetailsResponse: MutableList<Fact> = mutableListOf()
//                    if (responseByRandom.isSuccessful) {
//
//                        responseByRandom.body()?.let { factsResultResponse ->
//
//                            factDetailsResponse.add(factsResultResponse.getFactModel())
//
//                        }
//                        factsResult(FactsResult.Success(factDetailsResponse))
//                    } else {
//                        factsResult(FactsResult.ApiError(responseByRandom.code()))
//                    }
//                } catch (e: Exception) {
//                    factsResult(FactsResult.ServerError)
//                }
//            } else {
//                factsResult(noNetworkConnectivityError())
//            }
//        }
//    }
//
//    override suspend fun getCategoriesList(
//        factsResult: (result: FactsResult<List<String>>) -> Unit
//    ) {
//        CoroutineScope(IO).launch {
//            if (isOnline(context)) {
//
//                val responseCategories = api.getCategoriesList()
//
//                try {
//                    val categoriesList: MutableList<String> = mutableListOf()
//
//                    if (responseCategories.isSuccessful) {
//
//                        responseCategories.body()?.let { factsResultResponse ->
//                            for (result in factsResultResponse) {
//                                categoriesList.add(result)
//                            }
//                        }
//                        factsResult(FactsResult.Success(categoriesList))
//
//                    } else {
//                        factsResult(FactsResult.ApiError(responseCategories.code()))
//                    }
//
//                } catch (e: Exception) {
//                    factsResult(FactsResult.ServerError)
//                }
//            } else {
//                factsResult(noNetworkConnectivityError())
//            }
//        }
//    }
}