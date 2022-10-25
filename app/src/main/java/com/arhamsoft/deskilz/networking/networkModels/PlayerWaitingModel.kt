package com.arhamsoft.deskilz.networking.networkModels

data class PlayerWaitingModel(
    val status: Int,
    val data: List<PlayerWaitingModelData>,
    val message: String
)
