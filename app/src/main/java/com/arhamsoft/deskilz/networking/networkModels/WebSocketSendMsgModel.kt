package com.arhamsoft.deskilz.networking.networkModels

data class WebSocketSendMsgModel(
    var roomId:Int? = 0,
    var userId:String? = "",
    var message:String? = ""
)
