package com.ssoft.iconsapp.view.notification.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssoft.iconsapp.model.repuest.NotificationRequest
import com.ssoft.iconsapp.model.response.NotificationResponse
import com.ssoft.iconsapp.model.response.RegisterData
import com.ssoft.iconsapp.repository.NotificationRept
import com.ssoft.iconsapp.view.register.viewModel.RegisterUi
import com.sukhom.charge_loma.util.LogUtil
import com.sukhom.charge_loma.util.SharedPreferenceUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class NotificationViewModel(val notificationRept: NotificationRept) :ViewModel() {


    private val notificationResponse = MutableLiveData<NotificationUi>()
    val onNotification:LiveData<NotificationUi> = notificationResponse


    fun getNotification(user:RegisterData){


        LogUtil.showLogError("dd","${user.toString()}")
        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->
            notificationResponse.value = NotificationUi.onError(throwable)
        }
        viewModelScope.launch(coroutineExceptionHanlder) {

            notificationResponse.value = NotificationUi.onLoading
            val response = notificationRept.getNotification(NotificationRequest("App123456",us_phone = user.app_phone,us_username = user.app_username))
           LogUtil.showLogError("si","${response.data.size}")

            notificationResponse.value = NotificationUi.onSuccess(response)

        }

    }


}

sealed class NotificationUi{
    data class onSuccess(val response:NotificationResponse):NotificationUi()
    data class onError(val throwable: Throwable):NotificationUi()
    object onLoading:NotificationUi()
}