package com.example.inzynierka.ui.tripPlans

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.ui.tripPlans.room.Trip
import com.example.inzynierka.ui.tripPlans.room.TripRepository
import kotlinx.coroutines.*

@Suppress("UNCHECKED_CAST")
class TripPlansViewModel constructor(application: Application): ViewModel() {

    class Factory constructor(
        private val app: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TripPlansViewModel(app) as T
        }
    }

    private var tripRepository: TripRepository =
        TripRepository(application)

    private var allTrip: Deferred<LiveData<List<Trip>>> =
        tripRepository.getAllTripAsync()


    fun insertTrip(trip: Trip) {
        tripRepository.insertTrip(trip)
    }

    fun updateTrip(trip: Trip) {
        tripRepository.updateTrip(trip)
    }

    fun deleteTrip(trip: Trip) {
        tripRepository.deleteTrip(trip)
    }

    fun deleteTripsList(trips: List<Trip>) {
        tripRepository.deleteTripsList(trips)
    }

    fun getAllTripAsync(): LiveData<List<Trip>> = runBlocking {
        allTrip.await()
    }


}
