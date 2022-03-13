package com.ssoft.iconsapp.share.slider

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import ss.com.bannerslider.ImageLoadingService

class PicassoImageLoadingService(context: Context) : ImageLoadingService {
    var context:Context? = null

    init {
        this.context = context

    }

    override fun loadImage(url: String?, imageView: ImageView?) {
//        Picasso.with(context).load(url).into(imageView);
        Glide.with(context!!).load(url).into(imageView!!)
    }

    override fun loadImage(resource: Int, imageView: ImageView?) {
        Glide.with(context!!).load(resource).into(imageView!!)
    }

    override fun loadImage(
        url: String?,
        placeHolder: Int,
        errorDrawable: Int,
        imageView: ImageView?
    ) {
        Glide.with(context!!).load(url).placeholder(placeHolder).error(errorDrawable).into(imageView!!)

    }
}