package com.ssoft.iconsapp.view.register

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.databinding.FragmentRegisterBinding
import com.ssoft.iconsapp.view.main.viewModel.MainViewModel
import com.ssoft.iconsapp.view.policy.PolicyActivity
import com.ssoft.iconsapp.view.register.viewModel.RegisterUi
import com.ssoft.iconsapp.view.register.viewModel.RegisterViewModel
import com.sukhom.charge_loma.util.LogUtil
import com.sukhom.charge_loma.util.SharedPreferenceUtil
import com.zine.ketotime.BaseClass.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var location = "0.0,0.0"

    override val res: Int
        get() = R.layout.fragment_register


    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        super.onViewReady(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        requestGpsLocationWithPermissionCheck()

        observRegister()
        viewModel.context(requireContext())


        acceptCK.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                val intent = Intent(requireContext(), PolicyActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_POLICY)
            } else {

            }

        }

        saveBT.setOnClickListener {

            viewModel.register(
                telET.text.toString().trim(),
                emailET.text.toString().trim(),
                passcodeET.text.toString().trim(),
                location,
                SharedPreferenceUtil.getTokenPref(requireContext()) ?: "",
                acceptCK.isChecked
            )

        }


    }


    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun requestGpsLocation() {
        LogUtil.showLogError("locate", "okk")

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

            location?.let {
                this.location = "${location.latitude},${location.longitude}"
            }
            LogUtil.showLogError("location", "${this.location}")

        }
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onCameraDenied() {
//        Toast.makeText(this, R.string.permission_camera_denied, Toast.LENGTH_SHORT).show()
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        // NOTE: delegate the permission handling to generated function
//        onRequestPermissionsResult(requestCode, permissions)
//    }

    fun observRegister() {

        viewModel.onRegisterUi.observe(this, Observer { state: RegisterUi ->

            when (state) {
                is RegisterUi.onSuccess -> {
                    hideDialog()
                    val create2: AlertDialog = AlertDialog.Builder(requireContext()).create()
                    create2.setTitle(resources.getString(R.string.register_title));
                    create2.setCancelable(false)


                    when (state.response.status) {
                        "SUCCESS" -> {

                            LogUtil.showLogError("data", "${state.response.toString()}")
                            SharedPreferenceUtil.saveUserData(
                                requireContext(), Gson().toJson(
                                    state.response.data.get(
                                        0
                                    )
                                )
                            )
                            mainViewModel.setSuccessRegister()
                            create2.setMessage(
                                resources
                                    .getString(R.string.register_successfuly)
                            )

                        }
                        "Error" -> {
                            create2.setMessage(
                                resources
                                    .getString(R.string.register_error_passcode)
                            )
                        }
                        "OVERLIMIT" -> {
                            create2.setMessage(
                                resources.getString(R.string.register_over_limit)
                            )

                        }
                        "REGISTERED" -> {
                            create2.setMessage(
                                resources.getString(R.string.register_error_already)
                            )
                        }
                        "NODATA" -> {
                            create2.setMessage(
                                resources.getString(R.string.register_error_notregister)
                            )
                        }

                    }

                    create2.setButton(-1,
                        resources.getString(R.string.register_ok),
                        DialogInterface.OnClickListener { dialogInterface, i -> })
                    create2.show()

                }
                is RegisterUi.onLoading -> {
                    showProgressDialog()
                }
                is RegisterUi.onError -> {
                    hideDialog()
                    showToast(state.throwable.message)

                }
                is RegisterUi.onValidate -> {
                    showToast(state.msg)
                }

            }


        })


    }

    val REQUEST_CODE_POLICY = 200


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_POLICY -> {
                val status = data?.getBooleanExtra("status", false)
                acceptCK.isChecked = status?.let {
                    it
                } ?: kotlin.run {
                    false
                }


            }
        }

    }

    fun refresh() {
        telET.setHint(R.string.hit_tel)

        passcodeET.setHint(R.string.hit_passcode)
        emailET.setHint(R.string.hit_email)
        saveBT.setText(R.string.register)
        acceptCK.setText(R.string.text_accept_condition)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }
}