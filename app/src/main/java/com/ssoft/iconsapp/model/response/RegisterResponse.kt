package com.ssoft.iconsapp.model.response

data class RegisterResponse(
    val `data`: List<RegisterData>,
    val status: String,
    val message: String

)

data class RegisterData(
    val app_phone: String,
    val app_name: String,
    val app_username: String,
    var app_checkcode: String,
    val app_email: String
)