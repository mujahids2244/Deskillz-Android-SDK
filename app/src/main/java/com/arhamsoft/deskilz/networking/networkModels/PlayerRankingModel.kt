package com.arhamsoft.deskilz.networking.networkModels

data class PlayerRankingModel(
    val status: Int,
    val message: String,
    val data: List<PlayerRankingModelData>
)
