package com.pliniodev.chucknorrisfacts.service.repository

import com.pliniodev.chucknorrisfacts.service.utils.FactsResult

interface ChuckNorrisRepository {
    suspend fun getByFreeQuery(query: String, factsResult: (result: FactsResult) -> Unit)

    suspend fun getByCategory(category: String, factsResult: (result: FactsResult) -> Unit)

    suspend fun getByRandom(factsResult: (result: FactsResult) -> Unit)
}