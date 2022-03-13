package com.ssoft.iconsapp.view.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.databinding.FragmentHomeBinding
import com.ssoft.iconsapp.share.slider.PicassoImageLoadingService
import com.ssoft.iconsapp.view.home.adapter.HomeAdapter
import com.ssoft.iconsapp.view.home.viewModel.FilterNews
import com.ssoft.iconsapp.view.home.viewModel.HeaderNewsUi
import com.ssoft.iconsapp.view.home.viewModel.HomeViewModel
import com.ssoft.iconsapp.view.home.viewModel.NewsUi
import com.zine.ketotime.BaseClass.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import ss.com.bannerslider.Slider

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var adapters: HomeAdapter
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapters = HomeAdapter(requireContext())

    }

    override val res: Int
        get() = R.layout.fragment_home

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        super.onViewReady(view, savedInstanceState)

        initView()
        observHeaderInfo()
        observNews()
        viewModel.getNews()
        viewModel.getHeader()

    }

    fun doubleClick() {
        Log.e("view", "${news_cv}")
        news_cv.smoothScrollToPosition(0);

//        news_cv
    }



    fun refreshIndex() {

        adapters.refreshIndex()
    }

    fun initView() {
        Slider.init(PicassoImageLoadingService(requireContext()));

        refresh.setOnRefreshListener {
            viewModel.getNews()
            viewModel.getHeader()
        }
        Log.e("view", "${news_cv}")

        news_cv.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            adapter = adapters
        }
        adapters.listenerFilter = {
            adapters.filterNews(it)
            viewModel.filterNews(it)
        }

    }


    fun observNews() {

        viewModel.onNews.observe(this, Observer { state: NewsUi ->

            when (state) {
                is NewsUi.onSuccess -> {


                    hideDialog()
                    refresh.isRefreshing = false
                    adapters.setNewsList(state.response.data)
                    Log.e("ss", "${state.response.data.size}")
                }
                is NewsUi.onError -> {
                    refresh.isRefreshing = false
                    hideDialog()
                    Log.e("ee", "${state.throwable.message}")

                }
                is NewsUi.onLoading -> {

                    if (!refresh.isRefreshing)
                        showProgressDialog()
                }

            }


        })

    }

    fun observHeaderInfo() {

        viewModel.onNewsHeader.observe(this, Observer { state: HeaderNewsUi ->

            when (state) {
                is HeaderNewsUi.onSuccess -> {
//                    hideDialog()
                    adapters.setNewsHeader(state.response.data, state.img)
                }
                is HeaderNewsUi.onError -> {
//                    hideDialog()
                    Log.e("ee", "${state.throwable.message}")
                }
                is HeaderNewsUi.onLoading -> {
//                    showProgressDialog()
                }

            }


        })

    }


}

