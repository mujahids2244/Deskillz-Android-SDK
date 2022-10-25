package com.arhamsoft.deskilz.networking.networkModels

data class SendMsgModel(
    val status: Int,
    val data: SendMsgModelData,
    val message: String
)