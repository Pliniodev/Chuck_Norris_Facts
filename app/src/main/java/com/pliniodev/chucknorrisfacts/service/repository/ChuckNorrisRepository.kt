package com.pliniodev.chucknorrisfacts.service.repository

import com.pliniodev.chucknorrisfacts.service.utils.FactsResult

interface ChuckNorrisRepository {
    suspend fun getFact(query: String, resultCallback: (result: FactsResult) -> Unit)
}