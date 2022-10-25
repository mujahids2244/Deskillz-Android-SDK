package com.arhamsoft.deskilz.networking.networkModels

data class ThemeCoreColorsModel(
    val backgroundGradiantColor: List<String>,
    val mainColor: String,
    val mainColorOpacity: Long,
    val mainDividerColor: String,
    val secondaryColor: String,
    val secondaryDividerColor: String,
    val tertiaryColor: String,
    val tertiaryColorOpacity: Long,
    val cellBackgroundColor: String,
    val profilePicBackGroundGradiantColor: List<String>
)
