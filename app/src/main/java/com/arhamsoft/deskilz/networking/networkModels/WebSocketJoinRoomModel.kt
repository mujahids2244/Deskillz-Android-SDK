package com.arhamsoft.deskilz.networking.networkModels

data class WebSocketJoinRoomModel(
    var gameId:String? = "",
    var userId:String? = "",
    var friendId:String? = "",
    var type:Int? = 0
)
