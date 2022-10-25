package com.arhamsoft.deskilz.networking.networkModels

data class SendMsgModelData(
    val message: String,
    val status: Boolean,

    val _id: String,
    val userFromId:String,
    val userToId :String,

    val chatType: Long,
    val createdAt: String,
    val updatedAt: String,

    val __v: Long
)
