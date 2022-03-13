package com.ssoft.iconsapp.network.api

import com.ssoft.iconsapp.model.response.NewsResponse
import com.ssoft.iconsapp.model.response.NotificationResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NotificationApi {

    @FormUrlEncoded
    @POST("app_getalert.asp")
    suspend fun getNotification(
        @Field("am_code")am_code:String,
        @Field("us_phone")us_phone:String,
        @Field("us_username")us_username:String
    ): NotificationResponse


}