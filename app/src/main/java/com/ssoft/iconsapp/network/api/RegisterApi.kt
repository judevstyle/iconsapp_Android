package com.ssoft.iconsapp.network.api

import com.ssoft.iconsapp.model.response.ChangePasscodeResponse
import com.ssoft.iconsapp.model.response.NewsResponse
import com.ssoft.iconsapp.model.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterApi {

    @FormUrlEncoded
    @POST("app_register.asp")
    suspend fun register(
        @Field("us_phone")us_phone:String,
        @Field("us_Email")us_Email:String,
        @Field("us_Code")us_Code:String,
        @Field("us_devicetoken")us_devicetoken:String,
        @Field("us_username")us_username:String,
        @Field("us_location")us_location:String,
        @Field("am_code")am_code:String,
        @Field("us_deviceinfo")us_deviceinfo:String,
        @Field("platform")platform:String,

        ): RegisterResponse


    @FormUrlEncoded
    @POST("app_editpasscode.asp")
    suspend fun changePasscode(
        @Field("us_phone")us_phone:String,
        @Field("us_Email")us_Email:String,
        @Field("us_Code")us_Code:String,
        @Field("us_oldCode")us_oldCode:String,
        @Field("am_code")am_code:String
    ): ChangePasscodeResponse






}