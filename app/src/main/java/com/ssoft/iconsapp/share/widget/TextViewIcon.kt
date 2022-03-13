package com.ssoft.iconsapp.share.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.ssoft.iconsapp.R
import com.sukhom.charge_loma.util.LogUtil
import kotlinx.android.synthetic.main.view_text_icon.view.*


class TextViewIcon : FrameLayout {

    //
    lateinit var icon: ImageView
    lateinit var textTV: CustomTextView

    private var drawable: Drawable? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs, 0) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TextViewIcon,
            0, 0
        )

        try {
            // Resources$NotFoundException if vector image
            // badge = a.getDrawable(R.styleable.PreferencesBadge_badge)
//            val drawableResId = a.getResourceId(R.styleable.TextViewIcon_tvi_icon, -1);
            LogUtil.showLogError("ee", "elll ")


            val  a = context.obtainStyledAttributes(attrs, R.styleable.TextViewIcon);
             drawable = a.getDrawable(R.styleable.TextViewIcon_tvi_icon)

        }
        finally {
            a.recycle()
        }
    }



    constructor(context: Context) :
            super(context, null)

    init {
        initializeStyledAttributes()
    }


    private fun initializeStyledAttributes() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.view_text_icon, this, true)
        icon = view.findViewById(R.id.iconView)
        textTV = view.findViewById(R.id.textTV)
        LogUtil.showLogError("imf", "${drawable}")

//        if (drawable != null) {
//            setBadge(drawable)
//        }

    }

    fun setView(icon: Drawable, title: String) {

        iconView.setImageDrawable(icon)
        textTV.text = title

    }

    fun setBadge(badge: Drawable?) {
        this.drawable = badge
        LogUtil.showLogError("imf", "${badge}")
        iconView.setImageDrawable(badge)

    }
}

