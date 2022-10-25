package com.arhamsoft.deskilz.networking.networkModels

import java.io.Serializable

data class EventsModelData(
//    val eventID: Long,
//    val eventName: String,
//    val eventDescription: String,
//    val eventImage: String,
//    val eventState: Boolean,
//    val eventTime: String,
//    val playerCount: Long,
//    val entryFee: Double,
//    val eventRewardPrizes: List<EventRewardPrize>

    val eventID: String,
    val eventName: String,
    val eventDescription: String,
    val eventTimeFrom: String,
    val eventTimeTo: String,
    val eventRewardPrizes: List<Any?>,
    val playerCount: String,
    val entryFee: Long

):Serializable
