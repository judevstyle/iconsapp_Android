package com.ssoft.iconsapp.view.home.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.databinding.ItemHeaderNewsBinding
import com.ssoft.iconsapp.databinding.ItemNewsBinding
import com.ssoft.iconsapp.model.response.HeaderNews
import com.ssoft.iconsapp.model.response.News
import com.ssoft.iconsapp.share.slider.MainSliderAdapter
import com.ssoft.iconsapp.view.home.viewModel.FilterNews
import com.sukhom.charge_loma.util.LogUtil
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.item_header_news.view.*
import java.text.DecimalFormat

class HomeAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val NEWS_ITEM = 1
    private val HEADER_ITEM = 2
    private var slideImg: ArrayList<String>? = null
    private var headerInfo: HeaderNews? = null
    private var newsList: List<News>? = null

    private var filterNews: FilterNews? = null


    var listenerFilter: ((filter: FilterNews) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val infate = LayoutInflater.from(parent.context)
        if (viewType == HEADER_ITEM) {
            val binding = ItemHeaderNewsBinding.inflate(infate, parent, false)
            return HeaderView(binding)
        } else {
            val binding = ItemNewsBinding.inflate(infate, parent, false)
            return NewsView(binding)
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return HEADER_ITEM
        return NEWS_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HeaderView) {

            headerInfo?.let {
                holder.bind(it)
            }

        } else if (holder is NewsView) {
            val data = newsList!!.get(position - 1)
            holder.bind(data)

        }


    }

    fun filterNews(filterNews: FilterNews) {
        this.filterNews = filterNews
    }


    override fun getItemCount(): Int {

        Log.e("dd", "size ${newsList?.size}")
        newsList?.let {
            return it.size + 1
        } ?: kotlin.run {
            return 1
        }

    }


    fun setNewsList(news: List<News>) {
        this.newsList = news
        notifyDataSetChanged()
    }

    fun setNewsHeader(headerNews: HeaderNews, imgs: ArrayList<String>) {
        this.headerInfo = headerNews
        this.slideImg = imgs
        notifyDataSetChanged()
    }


    fun refreshIndex() {
        notifyItemChanged(0)

    }

    inner class HeaderView(val view: ItemHeaderNewsBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(data: HeaderNews) {
           // view.data = data
            view.bannerSlider.setAdapter(MainSliderAdapter(slideImg))
            view.bannerSlider.setInterval(3000)
            view.bannerSlider.setLoopSlides(true)
            val myFormatter = DecimalFormat("#,###")
            val output: String = myFormatter.format(data.allqty.toInt())

LogUtil.showLogError("num","${output}")
            view.textNumberProjects.text = output
            view.textValueProjects.text = myFormatter.format(data.allvalue.toInt())
            view.textTotalMembers.text = myFormatter.format(data.allmember.toInt())
            view.textContractProjects.text = myFormatter.format(data.findcontractor.toInt())
            view.textDayProjects.text = myFormatter.format(data.perday.toInt())
            view.textWeekProjects.text = myFormatter.format(data.perweek.toInt())


            if (filterNews != null) {
                when (filterNews) {
                    FilterNews.ALL -> {
                        view.allFilter.isChecked = true
                    }
                    FilterNews.INFROMATION -> {
                        view.infomationFilter.isChecked = true

                    }
                    FilterNews.ASSET -> {
                        view.assetFilter.isChecked = true
                    }

                }

            }

            view.typeFilter.setOnCheckedChangeListener { chipGroup, i ->

                when (i) {
                    R.id.allFilter -> {
                        listenerFilter?.invoke(FilterNews.ALL)
                        LogUtil.showLogError("ship", "allFilter")

                    }
                    R.id.infomationFilter -> {
                        listenerFilter?.invoke(FilterNews.INFROMATION)

                        LogUtil.showLogError("ship", "infomationFilter")

                    }
                    R.id.assetFilter -> {
                        listenerFilter?.invoke(FilterNews.ASSET)

                        LogUtil.showLogError("ship", "assetFilter")

                    }


                }


            }


        }
    }

    inner class NewsView(val view: ItemNewsBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(data: News) {
            Glide.with(view.root).load(data.nw_picture1).into(view.imageView)
            view.data = data



            if (data.nw_website.equals("")) {
                view.webAction.visibility = View.GONE
            } else {
                view.webAction.visibility = View.VISIBLE
                view.webAction.setOnClickListener {

//                context
                    try {
                        val uri = Uri.parse("${data.nw_website}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)

                    }catch (ex: Exception){

                    }


                }
            }


        }
    }

}