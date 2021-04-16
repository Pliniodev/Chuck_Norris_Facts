package com.pliniodev.chucknorrisfacts.service.model

import com.google.gson.annotations.SerializedName

data class FactsResultModel (
    @SerializedName("total")
    val total: Int,

    @SerializedName("result")
    val result: List<FactModel>
)