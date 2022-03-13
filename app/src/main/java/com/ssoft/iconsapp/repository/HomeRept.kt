package com.ssoft.iconsapp.repository

import com.ssoft.iconsapp.model.repuest.NewsRequest
import com.ssoft.iconsapp.model.response.HeaderResponse
import com.ssoft.iconsapp.model.response.NewsResponse
import com.ssoft.iconsapp.network.api.NewsApi

interface HomeRept {

    suspend fun getNews(newsRequest: NewsRequest): NewsResponse
    suspend fun getHeader(newsRequest: NewsRequest): HeaderResponse
}

class HomeImp(val newsApi: NewsApi) : HomeRept {

    override suspend fun getNews(newsRequest: NewsRequest) = newsApi.getNews(newsRequest.us_lang,newsRequest.us_page,newsRequest.am_code)
    override suspend fun getHeader(newsRequest: NewsRequest) = newsApi.getHeader(newsRequest.us_lang,newsRequest.us_page,newsRequest.am_code)

}

