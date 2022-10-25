package com.arhamsoft.deskilz.networking.networkModels

data class GetChatsModelData(
    val userId: String,

    val username: String,
//    val userCountryImage: String?,
    val message: String,
    val userImage: String,
    val date: String,
    val time: String
)
