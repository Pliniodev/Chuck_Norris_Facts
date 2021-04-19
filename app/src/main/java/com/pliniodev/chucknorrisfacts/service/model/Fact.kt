package com.pliniodev.chucknorrisfacts.service.model

data class Fact(
    val categories: ArrayList<String>,
    val created_at: String,
    val icon_url: String,
    val id: String,
    val updated_at: String,
    val url: String,
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
        get() {
            if (categories.isEmpty()) {
                return "UNCATEGORIZED"
            }
            return categories.toString().replace("[\\[\\]]".toRegex(), "")
        }
}