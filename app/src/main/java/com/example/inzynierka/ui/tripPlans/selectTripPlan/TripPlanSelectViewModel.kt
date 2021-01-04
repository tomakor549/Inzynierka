package com.example.inzynierka.ui.tripPlans.selectTripPlan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.inzynierka.room.trip.TripRepository
import com.example.inzynierka.room.trip.TripWithPlans
import kotlinx.coroutines.runBlocking

class TripPlanSelectViewModel constructor(application: Application): AndroidViewModel(application) {

    private var tripRepository: TripRepository =
        TripRepository(application)

    fun getTripWithPlans(tripId: Long): TripWithPlans = runBlocking {
        tripRepository.getTripWithPlansAsync(tripId).await()
    }

    fun convertTripToString(tripPlan: TripWithPlans): String{
        val str = "Data wycieczki: " + tripPlan.trip.startDate + "-" +
                tripPlan.trip.endDate + ":\n\n" +
                "Plan wycieczki:" + "\n"

        var plans: String = ""
        for (plan in tripPlan.plans){
            plans += "Dzień " + plan.day.toString() + ":\n" +
                    plan.description + "\n\n"
        }
        return str+plans

    }
}