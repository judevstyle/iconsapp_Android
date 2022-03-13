package com.ssoft.iconsapp.view.profile

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.databinding.FragmentProfileBinding
import com.ssoft.iconsapp.model.response.RegisterData
import com.ssoft.iconsapp.view.main.viewModel.MainViewModel
import com.ssoft.iconsapp.view.register.viewModel.ChangePasscodeUi
import com.ssoft.iconsapp.view.register.viewModel.RegisterUi
import com.ssoft.iconsapp.view.register.viewModel.RegisterViewModel
import com.sukhom.charge_loma.util.LogUtil
import com.sukhom.charge_loma.util.SharedPreferenceUtil
import com.zine.ketotime.BaseClass.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: RegisterViewModel by viewModel()


    var userData:RegisterData? = null
    private val mainViewModel: MainViewModel by sharedViewModel()

    override val res: Int
        get() = R.layout.fragment_profile

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        super.onViewReady(view, savedInstanceState)
        initView()

        observChangePasscode()
    }



    fun initView(){


        viewModel.context(requireContext())

        SharedPreferenceUtil.getUserData(requireContext())?.let { data->
            this.userData = data
            nameTI.setView(resources.getDrawable(R.drawable.ic_user,null),"${data.app_name}")
            telTI.setView(resources.getDrawable(R.drawable.ic_mobile,null),"${data.app_phone}")
            emailTI.setView(resources.getDrawable(R.drawable.ic_email,null),"${data.app_email}")

        }

        editAction.setOnClickListener {
            contentPassCode.visibility = View.VISIBLE
            codeTI.visibility = View.GONE
            editAction.visibility = View.GONE
        }
        cancelBT.setOnClickListener {
            contentPassCode.visibility = View.GONE
            codeTI.visibility = View.VISIBLE
            editAction.visibility = View.VISIBLE
            passcodeET.setText("")
        }
        saveBT.setOnClickListener {
            val data:RegisterData? =  SharedPreferenceUtil.getUserData(requireContext())
            data?.let {
                viewModel.changePasscode(it,passcodeET.text.toString())
            }

        }



    }

    fun refresh(){
        editTV.text = getString(R.string.edit)
        titleTV.text = getString(R.string.register_successfuly)
        saveBT.text = getString(R.string.save)
        cancelBT.text = getString(R.string.cancel)
        passcodeET.setHint(R.string.input_new_passcode)

    }

    fun observChangePasscode() {

        viewModel.onChangePassCodeUi.observe(this, Observer { state: ChangePasscodeUi ->

            when (state) {
                is ChangePasscodeUi.onSuccess -> {
                    hideDialog()
                    val create2: AlertDialog = AlertDialog.Builder(requireContext()).create()
                    create2.setTitle(resources.getString(R.string.changed_passcode_title));
                    create2.setCancelable(false)


                    when(state.response.status){
                        "SUCCESS"->{

                            LogUtil.showLogError("data","${state.response.toString()}")
                          val data:RegisterData? =  SharedPreferenceUtil.getUserData(requireContext())
                            data?.let {
                                data.app_checkcode = passcodeET.text.toString()
                                SharedPreferenceUtil.saveUserData(requireContext(), Gson().toJson(data))

                            }
                            passcodeET.setText("")
                            mainViewModel.setSuccessRegister()
                            create2.setMessage(
                                resources
                                    .getString(R.string.changed_passcode)
                            )
                        }
                        "Error"->{
                            create2.setMessage(
                                getString(R.string.change_passcode_error)
                            )
                        }


                    }

                    create2.setButton(-1,
                        resources.getString(R.string.register_ok),
                        DialogInterface.OnClickListener { dialogInterface, i -> })
                    create2.show()

                }
                is ChangePasscodeUi.onLoading -> {
                    showProgressDialog()
                }
                is ChangePasscodeUi.onError -> {
                    hideDialog()
                    showToast(state.throwable.message)

                }
                is ChangePasscodeUi.onValidate -> {
                    showToast(state.msg)
                }

            }


        })


    }

}