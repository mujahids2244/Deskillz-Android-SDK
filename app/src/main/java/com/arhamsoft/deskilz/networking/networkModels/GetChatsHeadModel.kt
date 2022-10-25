package com.arhamsoft.deskilz.networking.networkModels

data class GetChatsHeadModel(
    val status: Int,
    val message: String,
    val data: List<GetChatsHeadModelData>
)
