package com.pliniodev.chucknorrisfacts.service.repository

import com.pliniodev.chucknorrisfacts.data.api.ChuckNorrisApi
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
}