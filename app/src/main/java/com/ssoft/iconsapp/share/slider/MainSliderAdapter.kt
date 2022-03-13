package com.ssoft.iconsapp.share.slider

import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder

class MainSliderAdapter(val arr: ArrayList<String>?) : SliderAdapter() {
    override fun getItemCount(): Int {
        arr?.let {
            return it.size
        }?: kotlin.run {
            return 0
        }

    }

    override fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder?) {
        imageSlideViewHolder?.bindImageSlide("${arr?.get(position)}");

    }
}