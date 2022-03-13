package com.ssoft.iconsapp.view.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssoft.iconsapp.model.repuest.NewsRequest
import com.ssoft.iconsapp.model.response.HeaderNews
import com.ssoft.iconsapp.model.response.HeaderResponse
import com.ssoft.iconsapp.model.response.News
import com.ssoft.iconsapp.model.response.NewsResponse
import com.ssoft.iconsapp.repository.HomeRept
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class HomeViewModel(val homeRept: HomeRept) : ViewModel() {


    private val newsData = MutableLiveData<NewsUi>()
    val onNews:LiveData<NewsUi> = newsData


    private val newsType2Data:ArrayList<News> =   ArrayList()
    private val newsType3Data:ArrayList<News> =   ArrayList()
    private var newsAllData:ArrayList<News> =   ArrayList()
    private var filterNews:FilterNews? = null





    private val newsHeaserData = MutableLiveData<HeaderNewsUi>()
    val onNewsHeader:LiveData<HeaderNewsUi> = newsHeaserData


    fun getNews(){
        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->
            newsData.value = NewsUi.onError(throwable)
        }

        viewModelScope.launch(coroutineExceptionHanlder) {
            newsData.value = NewsUi.onLoading
            val data = homeRept.getNews(NewsRequest())
         //   newsAllData = data.data

            for (news in data.data){
                newsAllData.add(news)
                if (news.nw_category == 2)
                    newsType2Data.add(news)
                else if (news.nw_category == 3)
                    newsType3Data.add(news)
            }
            this@HomeViewModel.filterNews?.let {
                filterNews(it)
            }?: kotlin.run {
                newsData.value = NewsUi.onSuccess(data)

            }
        }

    }







    fun getHeader(){
        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->
            newsHeaserData.value = HeaderNewsUi.onError(throwable)
        }

        viewModelScope.launch(coroutineExceptionHanlder) {
            newsHeaserData.value = HeaderNewsUi.onLoading
            val data = homeRept.getHeader(NewsRequest())
            newsHeaserData.value = HeaderNewsUi.onSuccess(data,packImage(data.data))
        }


    }

    fun packImage(headerNews: HeaderNews) : ArrayList<String>{
        val arr = ArrayList<String>()
        if (!headerNews.image1.equals(""))
            arr.add(headerNews.image1)
        if (!headerNews.image2.equals(""))
            arr.add(headerNews.image2)
        if (!headerNews.image3.equals(""))
            arr.add(headerNews.image3)
        return arr

    }


    fun filterNews(filterNews:FilterNews){

        this.filterNews = filterNews

        val data = when(filterNews){
            FilterNews.ALL ->{
                NewsResponse(newsAllData,",","")
            }
            FilterNews.ASSET ->{
                NewsResponse(newsType2Data,",","")
            }
            FilterNews.INFROMATION ->{
                NewsResponse(newsType3Data,",","")
            }
        }
        newsData.value = NewsUi.onSuccess(data)



    }




}


enum class FilterNews{
    ALL,
    ASSET,
    INFROMATION


}

sealed class NewsUi{
    data class onSuccess(val response:NewsResponse):NewsUi()
    data class onError(val throwable: Throwable):NewsUi()
    object onLoading:NewsUi()

}

sealed class HeaderNewsUi{
    data class onSuccess(val response:HeaderResponse,val img:ArrayList<String>):HeaderNewsUi()
    data class onError(val throwable: Throwable):HeaderNewsUi()
    object onLoading:HeaderNewsUi()

}