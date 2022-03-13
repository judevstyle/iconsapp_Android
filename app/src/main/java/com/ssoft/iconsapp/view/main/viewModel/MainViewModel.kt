package com.ssoft.iconsapp.view.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val stateRegister = MutableLiveData<Boolean>()
    val onRegister:LiveData<Boolean> = stateRegister


    init {
        stateRegister.value = false
    }


    fun setSuccessRegister(){
        stateRegister.value = true
    }




}