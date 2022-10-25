package com.arhamsoft.deskilz.networking.networkModels

data class GetMarketLoadMoreModelData(
    val marketId: String,
    val marketName: String,
    val offset: Long,
    var productInfo: ArrayList<ProductInfoModel>
)
