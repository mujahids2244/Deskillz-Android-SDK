package com.arhamsoft.deskilz.networking.networkModels

data class GetChatsHeadModelData(
    val userId: String,
    val username: String,
    val userCountryImage: String,
    val userImage: String,
    val lastSeen: String,
    val lastMessage: String
)
