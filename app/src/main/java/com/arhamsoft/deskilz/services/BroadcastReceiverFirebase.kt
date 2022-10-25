package com.arhamsoft.deskilz.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BroadcastReceiverFirebase:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("intentCheck= ", "onReceive:$intent ", )
        Log.e("intentCheck= ", "onReceive:${intent?.extras} ", )
    }
}