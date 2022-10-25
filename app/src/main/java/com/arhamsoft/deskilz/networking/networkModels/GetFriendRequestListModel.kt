package com.arhamsoft.deskilz.networking.networkModels

data class GetFriendRequestListModel(
    val status: Int,
    val data: List<GetFriendRequestListModelData> ,
    val message: String

)
