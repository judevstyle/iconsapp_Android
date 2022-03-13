package com.ssoft.iconsapp.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.media.RingtoneManager
import android.os.Build
import android.os.PowerManager
import android.util.Log
import android.view.View.MeasureSpec.EXACTLY
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.helper.AvtivityActive
import com.ssoft.iconsapp.view.main.MainActivity
import com.ssoft.iconsapp.view.notification.NotificationActivity
import com.ssoft.iconsapp.view.splash.SplashActivity
import com.sukhom.charge_loma.util.LogUtil
import kotlinx.coroutines.NonCancellable.isActive
import java.text.SimpleDateFormat
import java.util.*


class iCONSFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "iCONSFirebaseService"

    val GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL"

    private fun scheduleJob() {}

    private fun sendRegistrationToServer(str: String) {}

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        Log.e(TAG, "From: " + remoteMessage.from)
        Log.e("state data ","${remoteMessage.data.size }")
        if (remoteMessage.data.size > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.data)
            handleNow(remoteMessage.notification, remoteMessage.data)
            return
        }
//        Log.e(TAG, "Message Notification Body: " + remoteMessage.notification!!.body)
        sendNotification(
            remoteMessage.notification!!.title, remoteMessage.notification!!
                .body, remoteMessage.notification!!.body, remoteMessage.notification!!.body
        )
    }

    override fun onNewToken(str: String) {
//        Log.d(TAG, "Refreshed token: $str")
        sendRegistrationToServer(str)
    }

    private fun handleNow(notification: RemoteMessage.Notification?, map: Map<String, String?>) {
        var str: String?
        var str2: String?
        val str3: String?
        Log.e(TAG, "handleNow...")
        var str4: String? = ""
        if (notification != null) {
            str2 = notification.title
            str = notification.body
        } else {
            str = str4
            str2 = str
        }
        if (map.size > 0) {
            str2 = map["title"]
            str = map["body"]
            val str5 = if (map["type"] != null) map["type"] else str4
            if (map["message"] != null) {
                str4 = map["message"]
            }
            str3 = str4
            str4 = str5
        } else {
            str3 = str4
        }
        Log.e("TAG", "sendNotification...title: $str2, body: $str, type: $str4, message: $str3")
        if (str4 == "CODE" || str4 == "RequestAllow" || str4 == "ResponseAllow") {
//            val powerManager = getSystemService("power") as PowerManager
//            val isScreenOn = powerManager.isScreenOn
//            Log.e("MainApplication", "isScreenOn$isScreenOn")
//            if (!isScreenOn) {
//                powerManager.newWakeLock(805306394, "iCONS:WakeLock").acquire(120000)
//                powerManager.newWakeLock(1, "iCONS:CpuLock").acquire(120000)
//            }

    Log.e("state","${AvtivityActive.isCodeActivty()},${AvtivityActive.isMainActivty()}")
            if (AvtivityActive.isCodeActivty()) {
                val intent = Intent("openNotification")
                intent.putExtra("pushnotification", "YES")
                intent.putExtra("type", str4)
                intent.putExtra("message", str3)

                LogUtil.showLogError("broadcastReceiver 1","runn")
                sendBroadcast(intent)


            } else if (AvtivityActive.isMainActivty()) {
                LogUtil.showLogError("broadcastReceiver 2","runn")

                val intent2 = Intent("openNotification")
                intent2.putExtra("pushnotification", "YES")
                intent2.putExtra("type", str4)
                intent2.putExtra("message", str3)
                sendBroadcast(intent2)
            } else {
                sendNotification(str2, str, str4, str3)
            }
        } else {
            sendNotification(str2, str, str4, str3)
        }

     //   Log.e("state", "${AvtivityActive.isMainActivty()}")
//        if (AvtivityActive.isMainActivty()) {
//            val intent = Intent("openNotification")
//            intent.putExtra("type","otp")
//            intent.putExtra("message",str3)
//
//            sendBroadcast(intent)
//        } else
          //  sendNotification(str2, str, str4, str3)


    }

    private fun sendNotification(str: String?, str2: String?, str3: String?, str4: String?) {
        Log.e(TAG, "sendNotification...")
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("pushnotification", "YES")
        intent.putExtra("type", str3)
        intent.putExtra("message", str4)
        val contentIntent = NotificationCompat.Builder(this as Context, "Message")
            .setSmallIcon(R.mipmap.ic_launcher as Int).setContentTitle(str)
            .setContentText(str2).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(2))
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setGroup("aaa")
            .build()

        val groupNotificationBuilder = NotificationCompat.Builder(this, "Message")
            .setSmallIcon(R.mipmap.ic_launcher as Int).setContentTitle(str)
            .setContentText(str2).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(2))
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setGroup("aaa")
            .setGroupSummary(true)
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        System.currentTimeMillis().toInt()
        NotificationManagerCompat.from(this).apply {
            notify(System.currentTimeMillis().toInt() ,contentIntent)
            // ไว้สำหรับแยก Group Notification
            notificationManager.notify(1, groupNotificationBuilder)

        }
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "Message",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        val dateNow = Calendar.getInstance().timeInMillis

        Log.e("id", "${dateNow.toInt()}")




    }


}