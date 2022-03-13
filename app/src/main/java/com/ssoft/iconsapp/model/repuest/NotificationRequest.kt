package com.ssoft.iconsapp.model.repuest

import com.ssoft.iconsapp.model.response.Notification

data class NotificationRequest(
    val am_code: String,
    val us_phone: String,
    val us_username: String,
    )