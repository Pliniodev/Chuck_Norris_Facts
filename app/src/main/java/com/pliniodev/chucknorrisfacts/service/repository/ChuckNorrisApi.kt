package com.pliniodev.chucknorrisfacts.service.repository

import com.pliniodev.chucknorrisfacts.data.response.FactBodyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisApi {
    @GET("/jokes/search")
    suspend fun getFreeQuery(
        @Query(
            value = "query",
            encoded = true
        ) query: String
    ): Response<FactBodyResponse>
}