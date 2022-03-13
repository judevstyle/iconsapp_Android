/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sukhom.charge_loma.util

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.ssoft.iconsapp.R
import com.ssoft.iconsapp.model.response.RegisterData

/**
 * Returns the `location` object as a human readable string.
 */
fun Location?.toText(): String {
    return if (this != null) {
        "($latitude, $longitude)"
    } else {
        "Unknown location"
    }
}

/**
 * Provides access to SharedPreferences for location to Activities and Services.
 */


internal object LogUtil {

    fun showLogError(tag: String, value: String) {
        Log.e(tag, value)
    }

    fun showLogDebug(tag: String, value: String) {
        Log.d(tag, value)
    }

    fun showLogWarn(tag: String, value: String) {
        Log.w(tag, value)
    }

    fun showLogInfo(tag: String, value: String) {
        Log.i(tag, value)
    }

}

internal object SharedPreferenceUtil {

    const val KEY_FOREGROUND_ENABLED = "tracking_foreground_location"
    const val LAT_UPDATE = "lat_location"
    const val LNG_UPDATE = "lng_location"
    const val SHIPMENT_RUNING = "shipment_runing"
    const val TOKEN = "token"
    const val LOGIN_STATE = "login"
    const val SHIPMENT = "shipment_id"
    const val LANG = "lang"
    const val USER_DATA = "USER_DATA"


    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The [Context].
     */

    fun getShipmentIDTrackingPref(context: Context): Int =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getInt(SHIPMENT, 0)

    fun setShipmentIDTrackingPref(context: Context, shipment_id: Int) {

        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putInt(SHIPMENT, shipment_id)
        }


    }

    fun getLocationTrackingPref(context: Context): Boolean =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getBoolean(KEY_FOREGROUND_ENABLED, false)


    fun getShipmentRuning(context: Context): Boolean =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getBoolean(SHIPMENT_RUNING, false)

    /**
     * Stores the location updates state in SharedPreferences.
     * @param requestingLocationUpdates The location updates state.
     */

    fun saveRuningShipmentPref(context: Context, runingState: Boolean) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putBoolean(SHIPMENT_RUNING, runingState)
        }


    fun saveLocationTrackingPref(context: Context, requestingLocationUpdates: Boolean) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putBoolean(KEY_FOREGROUND_ENABLED, requestingLocationUpdates)
        }


    fun saveUserData(context: Context, data: String?) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putString(USER_DATA, data)
        }


    fun getUserData(context: Context): RegisterData? {
        val data = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).getString(USER_DATA, null)

        data?.let {
            val gson = Gson()
            return gson.fromJson(it, RegisterData::class.java)

        }
        return null

    }


    fun updateLocationTrackingPref(context: Context, location: Location) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putFloat(LAT_UPDATE, location.latitude.toFloat())
            putFloat(LNG_UPDATE, location.longitude.toFloat())
        }


    fun updateLANG(context: Context, lang: String) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putString(LANG, lang)
        }


    fun getLANG(context: Context): String? =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getString(LANG, "th")

//    fun getLocationPref(context: Context): LatLng {
//
//        val data = context.getSharedPreferences(
//            context.getString(R.string.preference_file_key),
//            Context.MODE_PRIVATE
//        )
//        return LatLng(data.getFloat(LAT_UPDATE,0.0.toFloat()).toDouble(),data.getFloat(LNG_UPDATE,0.0.toFloat()).toDouble())
//
//    }


    fun checkDistanceLocationTrackingPref(context: Context, location: Location) {

        val spf = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        val lat = spf.getFloat(LAT_UPDATE, 0.0.toFloat()).toDouble()
        val lng = spf.getFloat(LNG_UPDATE, 0.0.toFloat()).toDouble()

        val l1 = Location("One")
        l1.latitude = lat
        l1.longitude = lng

        val l2 = Location("Two")
        l2.latitude = location.latitude
        l2.longitude = location.longitude
        val distance = l1.distanceTo(l2)

        if (distance > 20) {
            updateLocationTrackingPref(context, location)
        }


    }

    fun updateTokenPref(context: Context, token: String) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putString(TOKEN, token)
        }


    fun getTokenPref(context: Context): String? =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getString(TOKEN, "")

    fun getLoginPref(context: Context): Boolean =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getBoolean(LOGIN_STATE, false)


    fun updateLoginPref(context: Context, state: Boolean) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .edit {
                putBoolean(LOGIN_STATE, state)
            }


}
