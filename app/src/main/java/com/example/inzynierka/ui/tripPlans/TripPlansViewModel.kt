package com.example.inzynierka.ui.tripPlans

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.room.Plan
import com.example.inzynierka.room.Trip
import com.example.inzynierka.room.TripRepository
import com.example.inzynierka.room.TripWithPlans
import kotlinx.coroutines.*

@Suppress("UNCHECKED_CAST")
class TripPlansViewModel constructor(application: Application): AndroidViewModel(application) {

    /*class Factory constructor(
        private val app: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TripPlansViewModel(app) as T
        }
    }*/

    lateinit var listOfTrip: LiveData<List<Trip>>

    private var tripRepository: TripRepository =
        TripRepository(application)

    fun getAllTrip(): LiveData<List<Trip>> = runBlocking {
        tripRepository.getAllTripAsync().await()
    }

}
