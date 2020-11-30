package com.example.inzynierka.ui.tripPlans

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.ui.profile.ProfileViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class TripPlansViewModel @Inject constructor(context: Context): ViewModel() {

    class Factory constructor(
        private val con: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(con) as T
        }
    }


}
