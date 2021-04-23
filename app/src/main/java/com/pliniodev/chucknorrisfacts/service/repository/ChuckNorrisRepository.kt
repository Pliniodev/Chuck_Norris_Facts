package com.pliniodev.chucknorrisfacts.service.repository

import com.pliniodev.chucknorrisfacts.data.response.FactBodyResponse
import com.pliniodev.chucknorrisfacts.data.response.FactDetailsResponse
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult

interface ChuckNorrisRepository {
    suspend fun getByFreeQuery(query: String, factsResult: (result: FactsResult<List<Fact>>) -> Unit)

    suspend fun getByCategory(category: String, factsResult: (result: FactsResult<List<Fact>>) -> Unit)

    suspend fun getByRandom(factsResult: (result: FactsResult<List<Fact>>) -> Unit)

    suspend fun getCategoriesList(factsResult: (result: FactsResult<List<String>>) -> Unit)
}