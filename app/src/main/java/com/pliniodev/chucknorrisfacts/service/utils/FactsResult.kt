package com.pliniodev.chucknorrisfacts.service.utils

import com.pliniodev.chucknorrisfacts.service.model.Fact

sealed class FactsResult {
    data class Success(val successData: List<Fact>) : FactsResult()
    data class ApiError(val statusCode: Int) : FactsResult()
    object ConnectionError : FactsResult()
    object ServerError : FactsResult()
}

