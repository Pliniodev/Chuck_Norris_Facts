package com.pliniodev.chucknorrisfacts.service.utils

/**
 * Sobre sealed classes
 * https://medium.com/@joelamalio/kotlin-sealed-class-aaf626e344e1
 */

sealed class FactsResult<out T> {
    data class Success<out T>(val successData: T) : FactsResult<T>()
    data class ApiError(val statusCode: Int, val error: ErrorResponse? = null) : FactsResult<Nothing>()
    object ConnectionError : FactsResult<Nothing>()
    object ServerError : FactsResult<Nothing>()
}

