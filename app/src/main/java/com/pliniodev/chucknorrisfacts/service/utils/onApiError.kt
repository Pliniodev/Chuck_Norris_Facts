package com.pliniodev.chucknorrisfacts.service.utils

import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.constants.Constants

fun onError(result: Int): Pair<Int, Int> {
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