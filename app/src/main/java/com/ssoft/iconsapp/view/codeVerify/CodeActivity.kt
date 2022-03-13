package com.ssoft.iconsapp.view.codeVerify

import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.helper.AvtivityActive
import com.ssoft.iconsapp.view.notification.NotificationActivity
import com.sukhom.charge_loma.util.LogUtil
import kotlinx.android.synthetic.main.activity_code.*
import kotlinx.android.synthetic.main.activity_code.toolbar
import kotlinx.android.synthetic.main.activity_notification.*


class CodeActivity : AppCompatActivity() {


//    private var binding: Any
    val TAG = "CodeActivity"

    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val str: String = this@CodeActivity.TAG
            Log.e(
                str,
                "broadcastReceiver..." + intent.extras?.getString("type") + intent.extras?.getString(
                    "message"
                )
            )
            val code = intent.extras?.getString("message")
            val type = intent.extras?.getString("type")


            if (type.equals("CODE")){
                updatePasscode(code?:"")

            }


//            if (intent.extras!!.getString("type") != "ResponseAllow") {
//                val intent2 = Intent(this@CodeActivity, CodeActivity::class.java)
//                intent2.putExtra("type", intent.extras!!.getString("type"))
//                intent2.putExtra("message", intent.extras!!.getString("message"))
//
//
//                this@CodeActivity.startActivity(intent2)
//            } else if (string!!.toInt() > 0) {
//                val create: AlertDialog = AlertDialog.Builder(this@CodeActivity).create()
//                create.setCancelable(false)
//                create.setTitle("Response Allow")
//                create.setMessage(string)
//                create.setButton(-1,
//                    this@CodeActivity.resources.getString(R.string.register_ok),
//                    DialogInterface.OnClickListener { dialogInterface, i ->
//                        try {
////                            GetProfile().execute(arrayOf<String?>(string))
//                        } catch (unused: Exception) {
//                        }
//                    })
//                create.show()
//            } else {
//                val create2: AlertDialog = AlertDialog.Builder(this@CodeActivity).create()
//                create2.setCancelable(false)
//                create2.setTitle("Response Allow")
//                create2.setMessage(string)
//                create2.setButton(-1,
//                    this@CodeActivity.resources.getString(R.string.register_ok),
//                    DialogInterface.OnClickListener { dialogInterface, i -> })
//                create2.show()
//            }
        }
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)


        toolbar.title = ""
        setSupportActionBar(toolbar)
        AvtivityActive.setCodeActivity(true)
        registerReceiver(broadcastReceiver, IntentFilter("openNotification"))


//        binding = DataBindingUtil.setContentView(this, R.layout.activity_code);
        Log.e("log","${this.getIntent().hasExtra("pushnotification")}")
        if (this.getIntent().hasExtra("pushnotification")) {
            Log.e("log","${this.getIntent().hasExtra("pushnotification")}")


            if (this.getIntent().getExtras()?.getString("type").equals("CODE")){
                updatePasscode(this.getIntent().getExtras()?.getString("message")?:"")
            }


        }

    }

    override fun onStart() {
        super.onStart()
        LogUtil.showLogError("slslsl","llll")


    }

    override fun onStop() {
        super.onStop()
        AvtivityActive.setCodeActivity(false)

//        unregisterReceiver(broadcastReceiver)

    }


    fun updatePasscode(code: String){

        if (code.length == 6){
//            binding?.code = code
            code1.text = code.get(0).toString()
            code2.text = code.get(1).toString()
            code3.text = code.get(2).toString()
            code4.text = code.get(3).toString()
            code5.text = code.get(4).toString()
            code6.text = code.get(5).toString()

        }



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