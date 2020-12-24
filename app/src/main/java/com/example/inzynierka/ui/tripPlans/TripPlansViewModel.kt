package com.example.inzynierka.ui.tripPlans

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.ui.tripPlans.room.Plan
import com.example.inzynierka.ui.tripPlans.room.Trip
import com.example.inzynierka.ui.tripPlans.room.TripRepository
import com.example.inzynierka.ui.tripPlans.room.TripWithPlans
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

    fun insertTrip(trip: Trip) {
        tripRepository.insertTrip(trip)
    }

    fun insertPlan(plan:Plan){
        tripRepository.insertPlan(plan)
    }

    fun updateTrip(trip: Trip) {
        tripRepository.updateTrip(trip)
    }

    fun updatePlan(plan: Plan) {
        tripRepository.updatePlan(plan)
    }

    fun deleteTrip(trip: Trip) {
        tripRepository.deleteTrip(trip)
    }

    fun deletePlan(plan: Plan) {
        tripRepository.deleteTrip(plan)
    }

    fun getAllTrip(): LiveData<List<Trip>> = runBlocking {
        tripRepository.getAllTripAsync().await()
    }

    fun getTripWithPlans(tripName: String): LiveData<TripWithPlans> = runBlocking {
        tripRepository.getTripWithPlansAsync(tripName).await()
    }


}
