package com.ssoft.iconsapp.view.notification.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.databinding.ItemHeaderNewsBinding
import com.ssoft.iconsapp.databinding.ItemNewsBinding
import com.ssoft.iconsapp.databinding.ItemNotificationBinding
import com.ssoft.iconsapp.model.response.HeaderNews
import com.ssoft.iconsapp.model.response.News
import com.ssoft.iconsapp.model.response.Notification
import com.ssoft.iconsapp.share.slider.MainSliderAdapter
import com.ssoft.iconsapp.view.home.viewModel.FilterNews
import com.sukhom.charge_loma.util.LogUtil
import kotlinx.android.synthetic.main.item_header_news.view.*

class NotificationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var notificationList: List<Notification>? = null

       var listenerClick: ((data:Notification)->Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val infate = LayoutInflater.from(parent.context)
        val binding = ItemNotificationBinding.inflate(infate, parent, false)
        return HolderView(binding)

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HolderView) {

            val data = notificationList?.get(position)
            data?.let {
                holder.bind(it)
            }

        }


    }

    override fun getItemCount(): Int {

        notificationList?.let {
            return it.size
        } ?: kotlin.run {
            return 0
        }

    }


    fun setNewsList(notifications: List<Notification>) {
        this.notificationList = notifications
        notifyDataSetChanged()
    }


    inner class HolderView(val view: ItemNotificationBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(data: Notification) {

            view.data = data

            view.layoutInfo.setOnClickListener {
                listenerClick?.invoke(data)

            }

        }
    }

}