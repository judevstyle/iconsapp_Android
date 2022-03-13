package com.ssoft.iconsapp.repository

import com.ssoft.iconsapp.model.response.ChangePasscodeResponse
import com.ssoft.iconsapp.model.response.RegisterResponse
import com.ssoft.iconsapp.network.api.RegisterApi
import retrofit2.http.Field

interface RegisterRept {

    suspend fun register(
        us_phone: String,
        us_Email: String,
        us_Code: String,
        us_devicetoken: String,
        us_username: String,
        us_location: String,
        am_code: String,
        us_deviceinfo: String,
        ): RegisterResponse


    suspend fun changePasscode(
        us_phone: String,
        us_Email: String,
        us_Code: String,
        us_oldCode: String,
        am_code: String,
    ): ChangePasscodeResponse


}

class RegisterImp(val api: RegisterApi) : RegisterRept {

    override suspend fun register(
        us_phone: String,
        us_Email: String,
        us_Code: String,
        us_devicetoken: String,
        us_username: String,
        us_location: String,
        am_code: String,
        us_deviceinfo: String

    ) = api.register(us_phone, us_Email, us_Code, us_devicetoken, us_username, us_location, am_code,us_deviceinfo,"Android")

    override suspend fun changePasscode(
        us_phone: String,
        us_Email: String,
        us_Code: String,
        us_oldCode: String,
        am_code: String
    ) = api.changePasscode(us_phone, us_Email, us_Code, us_oldCode, am_code)


}
