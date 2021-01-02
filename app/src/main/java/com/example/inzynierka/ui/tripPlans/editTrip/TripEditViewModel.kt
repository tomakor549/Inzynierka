package com.example.inzynierka.ui.tripPlans.editTrip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.inzynierka.ui.tripPlans.room.Plan
import com.example.inzynierka.ui.tripPlans.room.Trip
import com.example.inzynierka.ui.tripPlans.room.TripRepository
import com.example.inzynierka.ui.tripPlans.room.TripWithPlans
import kotlinx.coroutines.runBlocking

class TripEditViewModel constructor(application: Application): AndroidViewModel(application) {

    private var tripRepository: TripRepository =
        TripRepository(application)

    fun updateTrip(trip: Trip) {
        tripRepository.updateTrip(trip)
    }

    fun updatePlans(plans: List<Plan>){
        tripRepository.updatePlans(plans)
    }

    fun getTripWithPlans(id: Long): TripWithPlans = runBlocking {
        tripRepository.getTripWithPlansAsync(id).await()
    }


}