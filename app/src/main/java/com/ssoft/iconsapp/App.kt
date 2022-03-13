package com.ssoft.iconsapp

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssoft.iconsapp.network.NetworkModule
import com.ssoft.iconsapp.network.api.NewsApi
import com.ssoft.iconsapp.network.api.NotificationApi
import com.ssoft.iconsapp.network.api.RegisterApi
import com.ssoft.iconsapp.repository.*
import com.ssoft.iconsapp.view.home.viewModel.HomeViewModel
import com.ssoft.iconsapp.view.main.viewModel.MainViewModel
import com.ssoft.iconsapp.view.notification.viewModel.NotificationViewModel
import com.ssoft.iconsapp.view.register.viewModel.RegisterViewModel
import com.sukhom.charge_loma.util.SharedPreferenceUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initCoin()


//        Firebase.messaging.subscribeToTopic("weather")
//            .addOnCompleteListener { task ->
//
//            }
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
//                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            SharedPreferenceUtil.updateTokenPref(applicationContext,token)
            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
            Log.e("token",token)
//           ฝฝ Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initCoin() {
//
        startKoin {

            androidContext(this@App)


            val networkModule = module {
//                single{ HttpTKConnect()}

                single<NewsApi> {
                    NetworkModule().build()
                }
                single<RegisterApi> {

                    NetworkModule().build()
                }
                single<NotificationApi> {

                    NetworkModule().build()
                }


            }


            val model = module {

                single<HomeRept> {
                    HomeImp(get())
                }
                single<RegisterRept> {
                    RegisterImp(get())
                }

                single<NotificationRept> {
                NotificationImp(get())
            }


                single {
                    HomeImp(get())
                    RegisterImp(get())
                    NotificationImp(get())

                }


                viewModel {
                    HomeViewModel(get())

                }
                viewModel {
                    RegisterViewModel(get())
                }
                viewModel {
                    MainViewModel()
                }
                viewModel {
                    NotificationViewModel(get())
                }

            }

            modules(networkModule, model)


        }
    }


}