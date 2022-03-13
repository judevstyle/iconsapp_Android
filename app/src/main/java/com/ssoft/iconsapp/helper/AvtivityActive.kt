package com.ssoft.iconsapp.helper

import android.util.Log
import com.ssoft.iconsapp.view.main.MainActivity

class AvtivityActive {

    companion object{
        private var mainActivityActive:Boolean = false
        private var codeActivityActive:Boolean = false

        fun  isMainActivty(): Boolean{
            return mainActivityActive
        }
        fun setMainActivity(state:Boolean){
            mainActivityActive = state
        }

        fun  isCodeActivty(): Boolean{
            return codeActivityActive
        }
        fun setCodeActivity(state:Boolean){
            Log.e("statess","${state}")
            codeActivityActive = state
        }



    }

}