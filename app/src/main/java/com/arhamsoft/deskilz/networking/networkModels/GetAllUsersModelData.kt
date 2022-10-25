package com.arhamsoft.deskilz.networking.networkModels

import java.util.*

data class GetAllUsersModelData(
    val _id: String,
    val userId: String,
    val isFriendRequest: Boolean,
    val username: String? = null,
    val userImage: String? = null
)
