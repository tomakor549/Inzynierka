package com.example.inzynierka.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {

    private val _userName = MutableLiveData<String>().apply {
        value = "nazwa uzytkownika"
    }
    val userName: LiveData<String> = _userName

    fun getName(name: String){
        _userName.value = name
    }
}