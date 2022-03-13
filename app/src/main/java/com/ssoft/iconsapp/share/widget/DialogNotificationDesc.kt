package com.ssoft.iconsapp.share.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.model.response.Notification
import kotlinx.android.synthetic.main.dialog_desc_notification.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DialogNotificationDesc(val data:Notification) : DialogFragment() {

    var onCloseClickListener: (() -> Unit)? = null

//    companion object {
//        fun newInstance(): CustomRemarkDialog = CustomRemarkDialog().apply {
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
//        dialog?.let {
//            val width = WindowManager.LayoutParams.MATCH_PARENT-24
//            val height = WindowManager.LayoutParams.MATCH_PARENT-46
//            it.window?.setLayout(width, height)
//        }

        return inflater.inflate(R.layout.dialog_desc_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial()
    }

    private fun initial() {

       // dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Log.e("dw","wpwp ${ dialog?.window} , ${WindowManager.LayoutParams.MATCH_PARENT}")
        //setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);


        dialog?.let {
            val width = WindowManager.LayoutParams.MATCH_PARENT
            val height = WindowManager.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
            val margin = 82
            val back = ColorDrawable(Color.TRANSPARENT)

            val inset = InsetDrawable(back, margin)
            dialog!!.window!!.setBackgroundDrawable(inset)        }



        titleTV.text = "${data.alert_subject}"
        dateTV.text = "${data.alert_date}"
        descTV.text = "${data.alert_detail}"

        imageView4.setOnClickListener {
            onCloseClickListener?.invoke()
            dismiss()
        }


    }



}