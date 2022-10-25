package com.arhamsoft.deskilz.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.view.View
import androidx.lifecycle.LiveData
import com.arhamsoft.deskilz.AppController
import com.arhamsoft.deskilz.R
import com.google.android.material.snackbar.Snackbar

class InternetConLiveData(private val connectivityManager: ConnectivityManager):LiveData<Boolean>() {


    constructor(context: Context): this(
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)


    val networkCallback = object : ConnectivityManager.NetworkCallback(){


        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)


        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
//            Snackbar.make(View.inflate(AppController.getContext(),R.layout.snackbar_view,null),
//                "network unavailable ",Snackbar.LENGTH_INDEFINITE).show()

        }
    }


    override fun onActive() {
        super.onActive()
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }


    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)

    }
}