package com.ssoft.iconsapp.model.response

data class NotificationResponse(
    val `data`: List<Notification>,
    val status: String
)

data class Notification(
    val alert_date: String,
    val alert_detail: String,
    val alert_id: Int,
    val alert_subject: String,
    val alert_type: String
)