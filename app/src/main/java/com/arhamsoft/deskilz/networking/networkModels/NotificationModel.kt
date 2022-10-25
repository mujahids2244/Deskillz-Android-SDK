package com.arhamsoft.deskilz.networking.networkModels

data class NotificationModel(
    val status: Int,
    val data: List<NotificationModelData>,
    val message: String
)
