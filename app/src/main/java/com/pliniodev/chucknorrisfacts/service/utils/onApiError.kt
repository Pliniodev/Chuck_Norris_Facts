package com.pliniodev.chucknorrisfacts.service.utils

import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.constants.Constants

/**
 * o código de erro 400 já está sendo impedido pela validação feita no editSearch
 * seria portanto um tratamento de erro desnecessário
 */


fun onApiError(result: Int): Pair<Int, Int> {
    return when (result) {
        400 -> {
            Pair(Constants.VIEW_FLIPPER_ERROR, R.string.error_400)
        }
        404 -> {
            Pair(Constants.VIEW_FLIPPER_ERROR, R.string.error_404)
        }
        else -> {
            Pair(Constants.VIEW_FLIPPER_ERROR, R.string.error_generic)
        }
    }
}