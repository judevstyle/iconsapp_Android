package com.ssoft.iconsapp.view.preview

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.view.main.MainActivity
import java.util.*
import kotlin.concurrent.schedule


class PreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_preview)

        Timer("SettingUp", false).schedule(2000) {

            var intent: Intent = Intent(this@PreviewActivity, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        
        
    }
}