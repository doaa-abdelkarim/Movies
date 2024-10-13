package com.example.movies.util

import android.content.Context
import android.net.ConnectivityManager

interface NetworkHandler {

    fun isOnline(): Boolean
}

class NetworkHandlerImpl(val context: Context) : NetworkHandler {
    override fun isOnline(): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            cm.activeNetworkInfo?.isConnectedOrConnecting ?: false
        } catch (e: Exception) {
            false
        }
    }

}