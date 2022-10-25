package com.arhamsoft.deskilz.networking.networkModels

data class EventsModel(
    val status: Int,
    val message: String,
    val data: List<EventsModelData>
)
