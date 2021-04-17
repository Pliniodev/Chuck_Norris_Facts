package com.pliniodev.chucknorrisfacts.service.utils

import android.content.Context
import com.pliniodev.chucknorrisfacts.R

fun Context.noNetworkConnectivityError(): AppResult.Error {
    return AppResult.Error(Exception(this.resources.getString(R.string.no_network_connectivity)))
}
