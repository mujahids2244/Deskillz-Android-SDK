package com.arhamsoft.deskilz.networking.networkModels

data class SignUpModelData(
    val authToken: String,
    val userID: String,
    val userName: String,
    val userImage: String,
    val userCountry: String
)
