package com.ssoft.iconsapp.repository

import com.ssoft.iconsapp.model.repuest.NotificationRequest
import com.ssoft.iconsapp.model.response.NotificationResponse
import com.ssoft.iconsapp.network.api.NotificationApi

interface NotificationRept {

    suspend fun getNotification(request: NotificationRequest):NotificationResponse

}

class NotificationImp(val service:NotificationApi):NotificationRept{
    override suspend fun getNotification(request: NotificationRequest) =
        service.getNotification(request.am_code,request.us_phone,request.us_username)

}