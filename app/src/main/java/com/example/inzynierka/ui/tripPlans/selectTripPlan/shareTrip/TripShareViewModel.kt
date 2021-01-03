package com.example.inzynierka.ui.tripPlans.selectTripPlan.shareTrip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.inzynierka.room.TripRepository
import com.example.inzynierka.room.TripWithPlans
import kotlinx.coroutines.runBlocking

class TripShareViewModel(application: Application): AndroidViewModel(application) {

    private var tripRepository: TripRepository =
        TripRepository(application)

    fun getTripWithPlans(tripId: Long): TripWithPlans = runBlocking {
        tripRepository.getTripWithPlansAsync(tripId).await()
    }
}