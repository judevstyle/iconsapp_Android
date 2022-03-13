package com.ssoft.iconsapp.view.policy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssoft.iconsapp.R
import kotlinx.android.synthetic.main.activity_policy.*

class PolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)


        rejectAction.setOnClickListener {

            val intent = Intent()
            intent.putExtra("status",false)
            setResult(200,intent)
            finish()

        }

        acceptAction.setOnClickListener {
            val intent = Intent()
            intent.putExtra("status",true)
            setResult(200,intent)
            finish()
        }



    }
}