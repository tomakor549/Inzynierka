package com.example.inzynierka.ui.tripPlans.addTrip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.inzynierka.room.trip.Plan
import com.example.inzynierka.room.trip.Trip
import com.example.inzynierka.room.trip.TripRepository
import kotlinx.coroutines.runBlocking

class AddTripViewModel constructor(application: Application): AndroidViewModel(application) {


    private var tripRepository: TripRepository =
        TripRepository(application)

    fun insertTrip(trip: Trip) {
        tripRepository.insertTrip(trip)
    }

    fun insertPlan(plan: Plan){
        tripRepository.insertPlan(plan)
    }

    fun insertPlans(plans: List<Plan>){
        tripRepository.insertPlans(plans)
    }

    fun updateTrip(trip: Trip) {
        tripRepository.insertTrip(trip)
    }

    fun updatePlan(plan: Plan){
        tripRepository.insertPlan(plan)
    }

    fun getAllTripId(): List<Long> = runBlocking {
        tripRepository.getAllTripIdAsync().await()
    }


}