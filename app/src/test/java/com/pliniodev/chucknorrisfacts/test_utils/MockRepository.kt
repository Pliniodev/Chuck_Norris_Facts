package com.pliniodev.chucknorrisfacts.test_utils

import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult

class MockRepository(
    private val result: FactsResult<List<Any>>
) : ChuckNorrisRepository {
    override suspend fun getByFreeQuery(query: String): FactsResult<List<Fact>> {
        return result as FactsResult<List<Fact>>
    }

    override suspend fun getByCategory(category: String): FactsResult<List<Fact>> {
        return result as FactsResult<List<Fact>>
    }

    override suspend fun getByRandom(): FactsResult<List<Fact>> {
        return result as FactsResult<List<Fact>>
    }

    override suspend fun getCategoriesList(): FactsResult<List<String>> {
        return result as FactsResult<List<String>>
    }
}