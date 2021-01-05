package com.example.inzynierka.ui.profile

import android.content.Context
import androidx.lifecycle.*
import com.example.inzynierka.User
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ProfileViewModel @Inject constructor(context: Context) : ViewModel() {

    private var user = User(context)

    class Factory constructor(
        private val con: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(con) as T
        }
    }

    private val _editTextButton = MutableLiveData<String>().apply {
        value = "edytuj dane"
    }
    val editTextButton: LiveData<String> = _editTextButton

    //imie i nazwisko
    private val _userName = MutableLiveData<String>().apply {
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

    val sendUserData = user.getName() + "\nKrew: " + user.getBloodType() + "\nNumery ICE:\n" + user.getICE1() +
            "\n" + user.getICE2() + "\n" + user.getICE3() + "\nOstatnie choroby: " +
            user.getIllnesses() + "\nOstatnio zażywane leki: " + user.getMedicines()

    val sendTitle = "Dane osobiste"
}
