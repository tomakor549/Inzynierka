package com.example.inzynierka.ui.tripPlans.selectTripPlan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.inzynierka.ui.tripPlans.room.TripRepository
import com.example.inzynierka.ui.tripPlans.room.TripWithPlans
import kotlinx.coroutines.runBlocking

class TripPlanSelectViewModel constructor(application: Application): AndroidViewModel(application) {

    private var tripRepository: TripRepository =
        TripRepository(application)

    fun getTripWithPlans(tripId: Long): TripWithPlans = runBlocking {
        tripRepository.getTripWithPlansAsync(tripId).await()
    }
}