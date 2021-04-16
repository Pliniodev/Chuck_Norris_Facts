package com.pliniodev.chucknorrisfacts.service.model

import com.google.gson.annotations.SerializedName

data class FactModel (

    @SerializedName("categories")
    val categories: ArrayList<String>,

    @SerializedName("created_at")
    val created_at: String,

    @SerializedName("icon_url")
    val icon_url: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("updated_at")
    val updated_at: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("value")
    val value: String,
) {
    val isLongText: Boolean
        get() {
            if (value.count() > 80) {
                return true
            }
            return false
        }

    val getCategory: String
        get(){
            if (categories.isEmpty()) {
                return "UNCATEGORIZED"
            }
            return categories.toString().replace("[\\[\\]]".toRegex(), "")
        }
}