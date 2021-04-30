package com.pliniodev.chucknorrisfacts.service.repository

import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult

interface ChuckNorrisRepository {

    suspend fun getByFreeQuery(query: String): FactsResult<List<Fact>>

    suspend fun getByCategory(category: String): FactsResult<List<Fact>>

    suspend fun getByRandom(): FactsResult<List<Fact>>

    suspend fun getCategoriesList(): FactsResult<List<String>>
}