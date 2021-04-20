package com.pliniodev.chucknorrisfacts.service.utils

import com.pliniodev.chucknorrisfacts.R

object Validator {

    fun validateSearchText(search: String): Pair<Boolean, Int?> {
        when {
            search.isEmpty() -> {
                return Pair(false, R.string.you_need_search)
            }
            search.length < 3 -> {
                return Pair(false, R.string.min_search_characters)
            }
            search.length in 3..119 && !haveSymbols(search) -> {
                return Pair(true, R.string.yeah_its_a_search)
            }
            search.length > 120 -> {
                return Pair(false, R.string.max_search_characters)
            }
            else -> {
                return if (haveSymbols(search)) {
                    Pair(false, R.string.no_symbols)
                } else {
                    Pair(false, null)
                }

            }
        }
    }

    private fun haveSymbols(search: String): Boolean {
        return search.filterNot { it.isLetterOrDigit()}.count() > 0
    }
}