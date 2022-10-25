package com.arhamsoft.deskilz.networking.networkModels

data class GetChatsModel(
    val status: Int,
    val message: String,
    val data: List<GetChatsModelData>
)
