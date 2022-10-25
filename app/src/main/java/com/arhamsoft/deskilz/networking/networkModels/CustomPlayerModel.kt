package com.arhamsoft.deskilz.networking.networkModels

data class CustomPlayerModel( //getcustomplayerdata and getgamecustomdata same model
    val status: Int,
    val message: String,
    val data: List<CustomPlayerModelData>
)
