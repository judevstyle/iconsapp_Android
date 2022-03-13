package com.ssoft.iconsapp.model.response

data class NewsResponse(
    val `data`: List<News>,
    val status: String,
    val message: String

)

data class News(
    val RowNum: Int,
    val nw_category: Int,
    val nw_detail: String,
    val nw_id: Int,
    val nw_materialtype: String,
    val nw_picture1: String,
    val nw_title: String,
    val nw_update: String,
    val nw_website:String
)