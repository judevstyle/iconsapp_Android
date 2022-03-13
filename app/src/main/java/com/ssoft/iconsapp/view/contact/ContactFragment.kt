package com.ssoft.iconsapp.view.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.databinding.FragmentContactBinding
import com.zine.ketotime.BaseClass.BaseFragment
import kotlinx.android.synthetic.main.fragment_contact.*

class ContactFragment : BaseFragment<FragmentContactBinding>() {



    override val res: Int
        get() = R.layout.fragment_contact


    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        super.onViewReady(view, savedInstanceState)

        lineAction.setOnClickListener {

            val uri = Uri.parse("https://line.me/ti/p/@icons")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        }

        facebookAction.setOnClickListener {
            val uri = Uri.parse("https://www.facebook.com/iconsgroup")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        webAction.setOnClickListener {
            val uri = Uri.parse("https://icons.co.th/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        telAction.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:028108892")
            startActivity(intent)

        }

    }

    fun refresh(){

        timeService.text = getString(R.string.service_time)


    }


}