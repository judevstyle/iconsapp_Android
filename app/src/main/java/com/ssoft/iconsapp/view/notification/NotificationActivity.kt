package com.ssoft.iconsapp.view.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.share.slider.PicassoImageLoadingService
import com.ssoft.iconsapp.share.widget.DialogNotificationDesc
import com.ssoft.iconsapp.view.notification.adapter.NotificationAdapter
import com.ssoft.iconsapp.view.notification.viewModel.NotificationUi
import com.ssoft.iconsapp.view.notification.viewModel.NotificationViewModel
import com.sukhom.charge_loma.util.SharedPreferenceUtil
import com.sukhom.charge_loma.view.baseclass.BaseActivity
import kotlinx.android.synthetic.main.activity_notification.*
import org.koin.android.viewmodel.ext.android.viewModel
import ss.com.bannerslider.Slider

class NotificationActivity : BaseActivity() {

    private val viewmodel: NotificationViewModel by viewModel()
    lateinit var adapters: NotificationAdapter



    override fun getContentView(): Int {
       return R.layout.activity_notification
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        super.onViewReady(savedInstanceState, intent)

        toolbar.title = ""
        setSupportActionBar(toolbar)

        adapters = NotificationAdapter()
        observNotification()
        initView()


         SharedPreferenceUtil.getUserData(this)?.let {
            viewmodel.getNotification(it)
        }

    }

    fun initView() {

        refresh.setOnRefreshListener {
            SharedPreferenceUtil.getUserData(this)?.let {
                viewmodel.getNotification(it)
            }        }

        notification_cv.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            isNestedScrollingEnabled = false
            adapter = adapters
        }
        adapters.listenerClick = {

            val dialog = DialogNotificationDesc(it)
            dialog.show(supportFragmentManager,"")


        }

    }

    fun observNotification() {

        viewmodel.onNotification.observe(this, Observer {state:NotificationUi->
            when(state){
                is NotificationUi.onLoading->{
                    showProgressDialog()
                }
                is NotificationUi.onSuccess->{
                    refresh.isRefreshing = false
                    hideDialog()
                    adapters.setNewsList(state.response.data)

                }
                is NotificationUi.onError->{
                    hideDialog()
                    showToast(state.throwable.message)
                }

            }

        })
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_close, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_close -> {
                finish()
            }


        }

        return super.onOptionsItemSelected(item)
    }


}