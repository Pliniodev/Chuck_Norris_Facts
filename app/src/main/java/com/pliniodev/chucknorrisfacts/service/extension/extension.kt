package com.pliniodev.chucknorrisfacts.service.extension

import com.pliniodev.chucknorrisfacts.data.response.FactBodyResponse
import com.pliniodev.chucknorrisfacts.data.response.FactDetailsResponse
import com.pliniodev.chucknorrisfacts.service.model.Fact
import retrofit2.Response

/**
 * Esta classe transforma os objetos da camada DATA para DOMAIN
 * Adição de funçães estendidas para ajudar nos testes e separação de responsabilidades.
 */

fun Response<FactBodyResponse>.toFactsList(): List<Fact> {
    val factDetailsResponse: MutableList<Fact> = mutableListOf()
    if (this.isSuccessful) {
        this.body()?.let { factsResultResponse ->
            for (result in factsResultResponse.result) {
                val fact = result.getFactModel()
                factDetailsResponse.add(fact)
            }
        }
    }
    return factDetailsResponse
}

fun Response<FactDetailsResponse>.toFact(): List<Fact> {
    val factDetailsResponse: MutableList<Fact> = mutableListOf()
    if (this.isSuccessful) {
        this.body()?.let { factsResultResponse ->
            val fact = factsResultResponse.getFactModel()
            factDetailsResponse.add(fact)
        }
    }
    return factDetailsResponse
}

fun Response<List<String>>.toCategoriesList(): List<String> {
    val categoriesList: MutableList<String> = mutableListOf()
    if (this.isSuccessful) {
        this.body()?.let { factsResultResponse ->
            for (result in factsResultResponse) {
                categoriesList.add(result)
            }
        }
    }
    return categoriesList
}