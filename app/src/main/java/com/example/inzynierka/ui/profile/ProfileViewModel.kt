package com.example.inzynierka.ui.profile

import android.content.Context
import androidx.lifecycle.*
import com.example.inzynierka.User
import javax.inject.Inject

class ProfileViewModel @Inject constructor(context: Context) : ViewModel() {

    private var user = User(context)

    class Factory constructor(
        private val con: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(con) as T
        }
    }

    val buttonEditText = "edytuj dane"
    val buttonViewText = "zapisz"

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

}
