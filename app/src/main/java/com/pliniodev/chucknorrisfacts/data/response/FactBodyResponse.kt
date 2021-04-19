package com.pliniodev.chucknorrisfacts.data.response

import com.google.gson.annotations.SerializedName

data class FactBodyResponse(
    @SerializedName("total")
    val total: Int,

    @SerializedName("result")
    val result: List<FactDetailsResponse>
)