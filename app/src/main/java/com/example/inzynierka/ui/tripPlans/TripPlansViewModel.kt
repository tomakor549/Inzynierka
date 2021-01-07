package com.example.inzynierka.ui.tripPlans

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.inzynierka.room.trip.Trip
import com.example.inzynierka.room.trip.TripRepository
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

    private var tripRepository: TripRepository =
        TripRepository(application)

    fun getAllTrip(): LiveData<List<Trip>> = runBlocking {
        tripRepository.getAllTripAsync().await()
    }

    fun deleteTripAndPlansById(tripId: Long) {
        tripRepository.deletePlansById(tripId)
        tripRepository.deleteTripById(tripId)
    }

}
