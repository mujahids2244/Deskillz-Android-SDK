package com.arhamsoft.deskilz.networking.networkModels

data class GetRandomPlayerModel(
    val status: Int,
    val message: String,
    val data: GetRandomPlayerModelData
)
