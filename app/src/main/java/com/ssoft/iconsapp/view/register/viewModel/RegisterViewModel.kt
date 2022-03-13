package com.ssoft.iconsapp.view.register.viewModel

import android.content.Context
import android.os.Build
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.model.response.ChangePasscodeResponse
import com.ssoft.iconsapp.model.response.RegisterData
import com.ssoft.iconsapp.model.response.RegisterResponse
import com.ssoft.iconsapp.repository.RegisterRept
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch


class RegisterViewModel(val registerRept: RegisterRept) : ViewModel() {

    private val registerResponse = MutableLiveData<RegisterUi>()
    val onRegisterUi:LiveData<RegisterUi> = registerResponse

    private val changePassCodeResponse = MutableLiveData<ChangePasscodeUi>()
    val onChangePassCodeUi:LiveData<ChangePasscodeUi> = changePassCodeResponse



    lateinit var context:Context

    fun context(context: Context){
        this.context = context
    }

    fun register(
        us_phone: String,
        us_Email: String,
        us_Code: String,
        us_location: String,
        token: String,
        isAccept: Boolean = false
    ){


        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->
            registerResponse.value = RegisterUi.onError(throwable)
        }

        if (us_phone.isEmpty()){
            registerResponse.value = RegisterUi.onValidate(context.getString(R.string.request_tel))
        }else if (us_Email.isEmpty()){
            registerResponse.value = RegisterUi.onValidate(context.getString(R.string.request_email))

        }
        else if (!(us_Email).isEmailValid()){
            registerResponse.value = RegisterUi.onValidate(context.getString(R.string.register_error_email))
        }

        else if (us_Code.isEmpty()){
            registerResponse.value = RegisterUi.onValidate(context.getString(R.string.request_code))

        }else if (!isAccept){
            registerResponse.value = RegisterUi.onValidate(context.getString(R.string.request_accept))

        }else{
            viewModelScope.launch(coroutineExceptionHanlder) {

                registerResponse.value = RegisterUi.onLoading
                val response = registerRept.register(
                    us_phone,
                    us_Email,
                    us_Code,
                    "${token}",
                    "",
                    us_location,
                    "App123456",
                            Build.MODEL
                    )
                registerResponse.value = RegisterUi.onSuccess(response)

            }

        }

    }



    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }



    fun changePasscode(data: RegisterData, newPasscode: String){

        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->
            changePassCodeResponse.value = ChangePasscodeUi.onError(throwable)
        }

        if (newPasscode.isEmpty()){
            changePassCodeResponse.value = ChangePasscodeUi.onValidate(context.getString(R.string.input_new_passcode))
        }else if (newPasscode.length < 5){
            changePassCodeResponse.value = ChangePasscodeUi.onValidate(context.getString(R.string.passcode_limit_5_digit))
        }else{
            viewModelScope.launch(coroutineExceptionHanlder) {

                changePassCodeResponse.value = ChangePasscodeUi.onLoading
                val response = registerRept.changePasscode(
                    data.app_phone,
                    data.app_email,
                    newPasscode,
                    data.app_checkcode,
                    "App123456"
                )
                changePassCodeResponse.value = ChangePasscodeUi.onSuccess(response)

            }

        }

    }


}

sealed class RegisterUi{

    data class onSuccess(val response: RegisterResponse):RegisterUi()
    data class onError(val throwable: Throwable):RegisterUi()
    data class onValidate(val msg: String):RegisterUi()
    object onLoading:RegisterUi()

}



sealed class ChangePasscodeUi{

    data class onSuccess(val response: ChangePasscodeResponse):ChangePasscodeUi()
    data class onError(val throwable: Throwable):ChangePasscodeUi()
    data class onValidate(val msg: String):ChangePasscodeUi()
    object onLoading:ChangePasscodeUi()

}

