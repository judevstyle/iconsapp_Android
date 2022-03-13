package com.ssoft.iconsapp.view.main

import android.content.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.helper.AvtivityActive
import com.ssoft.iconsapp.share.widget.DoubleClickListener
import com.ssoft.iconsapp.share.widget.Menu
import com.ssoft.iconsapp.view.codeVerify.CodeActivity
import com.ssoft.iconsapp.view.contact.ContactFragment
import com.ssoft.iconsapp.view.home.HomeFragment
import com.ssoft.iconsapp.view.main.viewModel.MainViewModel
import com.ssoft.iconsapp.view.notification.NotificationActivity
import com.ssoft.iconsapp.view.profile.ProfileFragment
import com.ssoft.iconsapp.view.register.RegisterFragment
import com.sukhom.charge_loma.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var registerFragment: RegisterFragment
    lateinit var contactFragment: ContactFragment
    lateinit var profileFragment: ProfileFragment

    lateinit var active: Fragment
    var fm: FragmentManager = supportFragmentManager

    private val viewModel: MainViewModel by viewModel()

    var TAG = "MainActivity"

    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val str: String = this@MainActivity.TAG
            Log.e(
                str,
                "broadcastReceiver..." + intent.extras?.getString("type") + intent.extras?.getString(
                    "message"
                )
            )
            val string = intent.extras?.getString("message")
            if (intent.extras!!.getString("type") == "CODE") {
                val intent2 = Intent(this@MainActivity, CodeActivity::class.java)
                intent2.putExtra("pushnotification", "YES")
                intent2.putExtra("type", intent.extras!!.getString("type"))
                intent2.putExtra("message", intent.extras!!.getString("message"))
                this@MainActivity.startActivity(intent2)
            } else if (string!!.toInt() > 0) {
                val create: AlertDialog = AlertDialog.Builder(this@MainActivity).create()
                create.setCancelable(false)
                create.setTitle("Response Allow")
                create.setMessage(string)
                create.setButton(-1,
                    this@MainActivity.resources.getString(R.string.register_ok),
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        try {
//                            GetProfile().execute(arrayOf<String?>(string))
                        } catch (unused: Exception) {
                        }
                    })
                create.show()
            } else {
                val create2: AlertDialog = AlertDialog.Builder(this@MainActivity).create()
                create2.setCancelable(false)
                create2.setTitle("Response Allow")
                create2.setMessage(string)
                create2.setButton(-1,
                    this@MainActivity.resources.getString(R.string.register_ok),
                    DialogInterface.OnClickListener { dialogInterface, i -> })
                create2.show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkRuningPage()
        setContentView(R.layout.activity_main)


        val lang = SharedPreferenceUtil.getLANG(this)


        //  SharedPreferenceUtil.saveUserData(this,null)
//      val  fm  = supportFragmentManager
        if (savedInstanceState == null) {
            profileFragment = ProfileFragment()
            contactFragment = ContactFragment()
            registerFragment = RegisterFragment()
            homeFragment = HomeFragment()

            fm.beginTransaction().add(R.id.frameLayout, profileFragment, "4").hide(profileFragment)
                .commit()
            fm.beginTransaction().add(R.id.frameLayout, contactFragment, "3").hide(contactFragment)
                .commit()
            fm.beginTransaction().add(R.id.frameLayout, registerFragment, "2")
                .hide(registerFragment).commit()
            fm.beginTransaction().add(R.id.frameLayout, homeFragment, "1").commit()

        }
        observRegisterState()
        active = homeFragment

        toolbar.title = ""
        setSupportActionBar(toolbar)

        menuView.homeMenu.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick() {
                // homeFragment. doubleClick()
                Log.e("click", "onDoubleClick")
            }

            override fun onSingleClick() {
//                replaceFragment(HomeFragment())
                menuView.event(Menu.HOME)
                fm.beginTransaction().hide(active).show(homeFragment).commit()
                active = homeFragment
                Log.e("click", "onSingleClick")
            }

        })
//        menuView.homeMenu.setOnClickListener {
//            Log.e("test","tes")
//
//            menuView.event(Menu.HOME)
//        }
        menuView.registerMenu.setOnClickListener {
            menuView.event(Menu.REGISTER)


            SharedPreferenceUtil.getUserData(this)?.let {
                viewModel.setSuccessRegister()
            } ?: kotlin.run {
                fm.beginTransaction().hide(active).show(registerFragment).commit()
                active = registerFragment
            }


//            replaceFragment(ContactFragment())

        }
        menuView.contactMenu.setOnClickListener {
            menuView.event(Menu.CONTACT)
            fm.beginTransaction().hide(active).show(contactFragment).commit()
            active = contactFragment
        }

//        replaceFragment(HomeFragment())


    }

//    fun replaceFragment(fragment: Fragment){
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.frameLayout, fragment)
//            .commit()
//
//    }


    fun checkRuningPage() {

        if (this.getIntent().hasExtra("pushnotification")) {


            var intent = Intent(this, NotificationActivity::class.java)
            if (this.getIntent().getExtras()?.getString("type").equals("CODE")) {
                intent = Intent(this, CodeActivity::class.java)
                intent.putExtra(
                    "pushnotification",
                    this.getIntent().getExtras()?.getString("pushnotification")
                )
                intent.putExtra(
                    "type",
                    this.getIntent().getExtras()?.getString("type")
                )
                intent.putExtra(
                    "message",
                    this.getIntent().getExtras()?.getString("message")
                )
            }
            startActivity(intent)


        }
    }

    var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
//        super.onBackPressed()
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()

        Timer("SettingUp", false).schedule(2000) {
            this@MainActivity.doubleBackToExitPressedOnce = false


        }

    }


    override fun onStart() {
        super.onStart()
        AvtivityActive.setMainActivity(true)
        registerReceiver(broadcastReceiver, IntentFilter("openNotification"))
    }

    override fun onStop() {
        super.onStop()
        AvtivityActive.setMainActivity(false)

        unregisterReceiver(broadcastReceiver)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_notification -> {

                SharedPreferenceUtil.getUserData(this)?.let {
                    startActivity(Intent(this, NotificationActivity::class.java))
                } ?: kotlin.run {
                    Toast.makeText(this, "กรุณาลงทะเบียนก่อน", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.menu_lang_en -> {

                SharedPreferenceUtil.updateLANG(this, "en")
                changeLocale("en")

            }
            R.id.menu_lang_th -> {
                SharedPreferenceUtil.updateLANG(this, "th")
                changeLocale("th")

            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: android.view.Menu?): Boolean {

        if (SharedPreferenceUtil.getLANG(this).equals("th")){
            menu?.getItem(1)?.setVisible(true)
            menu?.getItem(2)?.setVisible(false)
        }else{
            menu?.getItem(1)?.setVisible(false)
            menu?.getItem(2)?.setVisible(true)


        }

        return super.onPrepareOptionsMenu(menu)
    }



    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        Log.e("ddd", "r")
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true
    }

    fun observRegisterState() {
        viewModel.onRegister.observe(this, Observer {

            if (it) {
                profileFragment.initView()
                fm.beginTransaction().hide(active).show(profileFragment).commit()
                active = profileFragment
            }

        })
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
        invalidateOptionsMenu();

        updateUiview()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            requireActivity().supportFragmentManager.beginTransaction().detach(this).commitNow();
//            requireActivity().supportFragmentManager.beginTransaction().attach(this).commitNow();
//        } else {
//            requireActivity().supportFragmentManager.beginTransaction().detach(this).attach(this).commit();
//        }
//
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//        val config = this.resources.configuration
//        config.setLocale(locale)
//        createConfigurationContext(config)
    }

    fun updateUiview(){
        menuView.refreshMenu()
        homeFragment.refreshIndex()
        registerFragment.refresh()
        profileFragment.refresh()
        contactFragment.refresh()

    }

}