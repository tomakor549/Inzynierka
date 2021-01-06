package com.example.inzynierka.ui.tripPlans.editTrip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.inzynierka.room.trip.Plan
import com.example.inzynierka.room.trip.Trip
import com.example.inzynierka.room.trip.TripRepository
import com.example.inzynierka.room.trip.TripWithPlans
import kotlinx.coroutines.runBlocking

class TripEditViewModel constructor(application: Application): AndroidViewModel(application) {

    private var tripRepository: TripRepository =
        TripRepository(application)

    fun updateTrip(trip: Trip) {
        tripRepository.updateTrip(trip)
    }

    fun deletePlansById(tripId: Long){
        tripRepository.deletePlansById(tripId)
    }

    fun insertPlans(plans: List<Plan>){
        tripRepository.insertPlans(plans)
    }

    fun getTripWithPlans(id: Long): TripWithPlans = runBlocking {
        tripRepository.getTripWithPlansAsync(id).await()
    }


}