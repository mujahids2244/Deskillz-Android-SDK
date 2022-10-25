package com.arhamsoft.deskilz.networking.networkModels

data class GetPlayerAccount( //getcustomplayerdata and getgamecustomdata same model
    val status: Int,
    val message: String,
    val data: GetPlayerAccountData)
