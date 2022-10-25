package com.arhamsoft.deskilz.networking.networkModels

data class GetRewards(
    val status: Long,
    val message: String,
    val data: List<PrizesModel>
)
