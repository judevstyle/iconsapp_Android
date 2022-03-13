package com.ssoft.iconsapp.model.response

data class HeaderResponse(
    val `data`: HeaderNews,
    val message: String,
    val status: String
)

data class HeaderNews(
    val allmember: String,
    val allqty: String,
    val allvalue: String,
    val findcontractor: String,
    val image1: String,
    val image2: String,
    val image3: String,
    val perday: String,
    val perweek: String
)