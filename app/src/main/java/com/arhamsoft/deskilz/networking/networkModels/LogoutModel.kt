package com.arhamsoft.deskilz.networking.networkModels

class LogoutModel(
    val status: Int=0,
    val success: Boolean= false,
    val error: Boolean=false,
    val message: String?=""
)