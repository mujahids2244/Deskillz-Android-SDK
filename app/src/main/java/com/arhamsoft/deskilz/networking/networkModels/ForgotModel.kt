package com.arhamsoft.deskilz.networking.networkModels

data class ForgotModel(
    val status: Int,
    val isParticipated:Boolean= false,
    val isFriend :Boolean= false,
    val isFriendRequest :Boolean= false,
    val message: String

)
