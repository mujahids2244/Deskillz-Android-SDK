package com.arhamsoft.deskilz.networking.networkModels

data class LoginModel(
    var status: Int,
    val message: String,
    val data: LoginModelData
)