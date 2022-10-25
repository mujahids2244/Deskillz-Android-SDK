package com.arhamsoft.deskilz.networking.networkModels

data class GetMarketLoadMoreModel(
    val status: Int,
    val message: String,
    val data: List<GetMarketLoadMoreModelData>
)
