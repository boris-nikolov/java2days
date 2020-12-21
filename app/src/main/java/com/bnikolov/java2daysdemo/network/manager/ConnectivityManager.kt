package com.bnikolov.java2daysdemo.network.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import com.bnikolov.java2daysdemo.network.callback.ConnectivityChangeListener

object ConnectivityManager {

    private var networkConnectivityChangeListeners: ArrayList<ConnectivityChangeListener?> =
        arrayListOf()

    private var connectivityManager: ConnectivityManager? = null

    private var networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            if (connectivityManager?.activeNetworkInfo == null) {
                for (networkConnectivityChangeListener in networkConnectivityChangeListeners) {
                    networkConnectivityChangeListener?.onConnectionLost()
                }
            }
        }

        override fun onAvailable(network: Network) {
            for (networkConnectivityChangeListener in networkConnectivityChangeListeners) {
                networkConnectivityChangeListener?.onConnectionAvailable()
            }
        }
    }

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnected
    }

    fun setCallback(networkConnectivityChangeListener: ConnectivityChangeListener) {
        networkConnectivityChangeListeners.add(networkConnectivityChangeListener)
    }

    fun removeCallback(networkConnectivityChangeListener: ConnectivityChangeListener) {
        networkConnectivityChangeListeners.remove(networkConnectivityChangeListener)
    }

    fun init(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        this.connectivityManager = connectivityManager

        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }
}