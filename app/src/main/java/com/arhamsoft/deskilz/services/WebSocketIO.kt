package com.arhamsoft.deskilz.services

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket

object SocketHandler {

     var mSocket: Socket? = null

    @Synchronized
    fun setSocket() {
        try {
            val options = IO.Options()
            options.reconnection = true //reconnection
            options.forceNew = true

            options.transports = arrayOf("websocket")
            options.path = "/socket.io/?EIO=4&"
//            options.port = 8081

            mSocket = IO.socket("",options)

//              /////for headers

////            val extraHeaders: MutableMap<String, String> = HashMap()
////            extraHeaders["x-auth-token"] = ("Bearer")
////           extraHeaders["DeSkillzSdkKey"] = ("00000067")
////           extraHeaders["Authorization"] = (" Bearer")
////             options.auth = extraHeaders


//              /////for headers
//            val extra: MutableMap<String,List<String>> = HashMap()
//            extra["x-auth-token"] = listOf("Bearer")
//            extra["DeSkillzSdkKey"] = listOf("00000067")
//            extra["Authorization"] = listOf(" Bearer")
//            options.extraHeaders = extra



        } catch (e: Exception) {
            Log.e("exception", "setSocket: ")
        }
    }



//    var server = app.listen(3000, {// options can go here
//        transports: ['xhr-polling']
//    });
//    io.use((socket, next) => {
//        let clientId = socket.handshake.headers['x-user-id'];
//    });

    @Synchronized
    fun getSocket(): Socket? {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket?.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket?.disconnect()
    }
}
