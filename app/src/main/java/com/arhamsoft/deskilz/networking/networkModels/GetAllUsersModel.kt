package com.arhamsoft.deskilz.networking.networkModels

data class GetAllUsersModel( //getrequest and getallusermodel same model
    val status: Int,
    val data: List<GetAllUsersModelData>,
    val message: String
)
