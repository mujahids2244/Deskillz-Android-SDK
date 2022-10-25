package com.arhamsoft.deskilz.networking.networkModels

data class AddFriendModelData(
    val status: Boolean,

    val _id: String,

    val fromUserId: String,

    val toUserId: String,

    val createdAt: String,
    val updatedAt: String,

    val __v: Long
)
