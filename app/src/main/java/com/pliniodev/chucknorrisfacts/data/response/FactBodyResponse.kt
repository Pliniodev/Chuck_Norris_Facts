package com.pliniodev.chucknorrisfacts.data.response

import com.google.gson.annotations.SerializedName
import com.pliniodev.chucknorrisfacts.data.response.FactDetailsResponse

data class FactBodyResponse(
    @SerializedName("total")
    val total: Int,

    @SerializedName("result")
    val result: List<FactDetailsResponse>
)