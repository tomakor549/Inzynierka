package com.example.inzynierka.ui.tripPlans.addTrip

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inzynierka.ui.tripPlans.adapters.TripPlansListAdapter
import com.example.inzynierka.ui.tripPlans.room.Plan
import com.example.inzynierka.ui.tripPlans.room.Trip
import com.example.inzynierka.ui.tripPlans.room.TripRepository
import kotlinx.android.synthetic.main.activity_add_trip.*
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