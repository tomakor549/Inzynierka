package com.example.inzynierka.ui.profile

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inzynierka.User

class ProfileViewModel: ViewModel() {

    /*private lateinit var user: User

    fun setUser(con: Context){
        this.user = User(con)
    }

    //imie i nazwisko
    private val _userName = MutableLiveData<String>().apply {
        //value = name
        value = user.getName()
    }
    val userName: LiveData<String> = _userName

    //numery ICE
    private val _userICE1= MutableLiveData<String>().apply {
        value = user.getICE1()
    }
    val userICE1: LiveData<String> = _userICE1

    private val _userICE2 = MutableLiveData<String>().apply {
        value = user.getICE2()
    }
    val userICE2: LiveData<String> = _userICE2

    private val _userICE3 = MutableLiveData<String>().apply {
        value = user.getICE3()
    }
    val userICE3: LiveData<String> = _userICE3

    private val _userBloodType = MutableLiveData<String>().apply {
        value = user.getBloodType()
    }
    val userBloodType: LiveData<String> = _userBloodType

    private val _userIllnesses = MutableLiveData<String>().apply {
        value = user.getIllnesses()
    }
    val userIllnesses: LiveData<String> = _userIllnesses


    private val _userMedicines = MutableLiveData<String>().apply {
        value = user.getMedicines()
    }
    val userMedicines: LiveData<String> = _userMedicines
*/
}