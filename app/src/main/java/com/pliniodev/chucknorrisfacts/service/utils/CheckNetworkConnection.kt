package com.pliniodev.chucknorrisfacts.service.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.pliniodev.chucknorrisfacts.R

object CheckNetworkConnection {
    /**
     * Método para checagem de conexão com a internet
     * caso a versão minima do app seja a MARSHMALOW (SDK23) ou anterior
     * é preciso fazer a checagem da versão.
     * Este projeto foi inicialmente setado para a versão OREO (SDK26)
     */

    fun isOnline(context: Context): Boolean {

        var result = false

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                ConnectivityManager?
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        connectivityManager?.run {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    else -> false
                }
            }
        }
//        else {
//            connectivityManager?.run {
//                val activeNetwork: NetworkInfo? = connectivityManager.activeNerWorkInfo
//                return activeNetwork?.isConnected == true
//            }
//        }

        return result
    }

    fun noNetworkConnectivityError(): FactsResult.ConnectionError {
        return FactsResult.ConnectionError
    }

}