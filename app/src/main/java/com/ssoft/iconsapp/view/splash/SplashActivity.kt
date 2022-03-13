package com.ssoft.iconsapp.view.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.view.main.MainActivity
import com.ssoft.iconsapp.view.preview.PreviewActivity
import com.sukhom.charge_loma.util.SharedPreferenceUtil
import java.util.*
import kotlin.concurrent.schedule


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash)
        val data = SharedPreferenceUtil.getLANG(this)!!
        changeLocale(data)


        Timer("SettingUp", false).schedule(2000) {

            var intent:Intent = Intent(this@SplashActivity, PreviewActivity::class.java)

            if (this@SplashActivity.getIntent().hasExtra("pushnotification")) {


                Log.e("data","${this@SplashActivity.getIntent().getExtras()?.getString("pushnotification")},${  this@SplashActivity.getIntent().getExtras()?.getString("type")},${this@SplashActivity.getIntent().getExtras()?.getString("message")}")
                intent = Intent(this@SplashActivity, MainActivity::class.java)
                intent.putExtra(
                    "pushnotification",
                    this@SplashActivity.getIntent().getExtras()?.getString("pushnotification")
                )
                intent.putExtra(
                    "type",
                    this@SplashActivity.getIntent().getExtras()?.getString("type")
                )
                intent.putExtra(
                    "message",
                    this@SplashActivity.getIntent().getExtras()?.getString("message")
                )
            }
            startActivity(intent)
            finish()

        }





    }


    fun changeLocale(language: String) {


        val config = resources.configuration
        val lang = language // your language code
        val locale = Locale(lang)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale)
        else
            config.locale = locale

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)


    }

}