package com.ssoft.iconsapp.network.api

import com.ssoft.iconsapp.model.repuest.NewsRequest
import com.ssoft.iconsapp.model.response.HeaderResponse
import com.ssoft.iconsapp.model.response.NewsResponse
import retrofit2.http.*

interface NewsApi {


    @FormUrlEncoded
    @POST("app_getheader.asp")
    suspend fun getHeader(
        @Field("us_lang")us_lang:String,
        @Field("us_page")us_page:Int,
        @Field("am_code")am_code:String    ): HeaderResponse

//    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("app_getnews.asp")
    suspend fun getNews(
        @Field("us_lang")us_lang:String,
        @Field("us_page")us_page:Int,
        @Field("am_code")am_code:String
    ):NewsResponse


}