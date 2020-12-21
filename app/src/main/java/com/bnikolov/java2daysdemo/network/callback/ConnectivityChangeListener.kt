package com.bnikolov.java2daysdemo.network.callback

interface ConnectivityChangeListener {

    fun onConnectionAvailable()

    fun onConnectionLost()
}