package com.ssoft.iconsapp.share.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.ssoft.iconsapp.R

class MenuViewItem : FrameLayout {


    lateinit var homeMenu:FrameLayout
    lateinit var registerMenu:FrameLayout
    lateinit var contactMenu:FrameLayout

    lateinit var homeTitle:CustomTextView
    lateinit var registerTitle:CustomTextView
    lateinit var contactTitle:CustomTextView

    lateinit var homeIcon:ImageView
    lateinit var registerIcon:ImageView
    lateinit var contactIcon:ImageView





    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?) :
            this(context, attrs, 0)
    constructor(context: Context) :
            this(context, null)

    init {
        initializeStyledAttributes()
    }


    private fun initializeStyledAttributes() {


        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.bottom_menu_item,this,true)
        homeMenu =  view.findViewById(R.id.homeAction)
        registerMenu =  view.findViewById(R.id.registerAction)
        contactMenu =  view.findViewById(R.id.contactAction)

        homeTitle =  view.findViewById(R.id.homeTitle)
        registerTitle =  view.findViewById(R.id.registerTitle)
        contactTitle =  view.findViewById(R.id.contactTitle)


        homeIcon =  view.findViewById(R.id.homeIcon)
        registerIcon =  view.findViewById(R.id.registerIcon)
        contactIcon =  view.findViewById(R.id.contactIcon)

        event(Menu.HOME)
    }


    fun refreshMenu(){

        homeTitle.text = resources.getString(R.string.menu_home)
        registerTitle.text = resources.getString(R.string.menu_member)
        contactTitle.text = resources.getString(R.string.menu_contact)

    }

    fun event(menu:Menu){
        resetBg()
        Log.e("test","te")
        val white = context.resources.getColor(R.color.white,null)

        when(menu){
            Menu.HOME ->{
                homeMenu.setBackgroundColor(context.resources.getColor(R.color.colorPrimary,null))
                homeTitle.setTextColor(white)
                homeIcon.setColorFilter(white)

            }
            Menu.REGISTER ->{
                registerMenu.setBackgroundColor(context.resources.getColor(R.color.colorPrimary,null))
                registerTitle.setTextColor(white)
                registerIcon.setColorFilter(white)
            }
            Menu.CONTACT ->{
                contactMenu.setBackgroundColor(context.resources.getColor(R.color.colorPrimary,null))
                contactTitle.setTextColor(white)
                contactIcon.setColorFilter(white)
            }
        }

    }

//    private fun resetBg(){}

    private fun resetBg(){

        homeMenu.setBackgroundColor(context.resources.getColor(R.color.colorPrimaryTrans,null))
        registerMenu.setBackgroundColor(context.resources.getColor(R.color.colorPrimaryTrans,null))
        contactMenu.setBackgroundColor(context.resources.getColor(R.color.colorPrimaryTrans,null))

        val primaryColor = context.resources.getColor(R.color.colorPrimary,null)
        homeTitle.setTextColor(primaryColor)
        registerTitle.setTextColor(primaryColor)
        contactTitle.setTextColor(primaryColor)

        homeIcon.setColorFilter(primaryColor)
        registerIcon.setColorFilter(primaryColor)
        contactIcon.setColorFilter(primaryColor)

    }



}

enum class Menu {
    HOME, REGISTER, CONTACT
}