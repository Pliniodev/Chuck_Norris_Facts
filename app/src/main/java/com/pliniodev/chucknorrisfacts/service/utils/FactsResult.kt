package com.pliniodev.chucknorrisfacts.service.utils

import com.pliniodev.chucknorrisfacts.service.model.Fact

sealed class FactsResult {
    data class Success(val successData: List<Fact>) : FactsResult()
    data class Error(val statusCode: Int) : FactsResult()
    data class ConnectionError(val msg: String) : FactsResult()
    object ServerError : FactsResult()
}

