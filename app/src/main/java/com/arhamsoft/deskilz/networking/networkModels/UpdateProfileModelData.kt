package com.arhamsoft.deskilz.networking.networkModels

data class UpdateProfileModelData(
    val userID: String,
    val userName: String,
    val userImage: String,
    val userCountry: String,
    val userCountryFlag: String, //base 64 string
    val userShoutOut: String
)
