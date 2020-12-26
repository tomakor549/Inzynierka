package com.example.inzynierka.ui.tripPlans.room

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class TripRepository(application: Application) {
    private var tripDao: TripDao

    init {
        val database = TripDatabase.getInstance(application.applicationContext)

        tripDao = database!!.tripDao()
    }

    fun insertTrip(trip: Trip) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.insertTrip(trip)
        }

    fun insertPlan(plan: Plan) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.insertPlan(plan)
        }

    fun updateTrip(trip: Trip) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.updateTrip(trip)
        }

    fun updatePlan(plan: Plan) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.updatePlan(plan)
        }

    fun deleteTrip(trip: Trip) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.deleteTrip(trip)
        }

    fun deleteTrip(plan: Plan) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.deletePlan(plan)
        }

    fun getAllTripAsync():Deferred<LiveData<List<Trip>>> =
        CoroutineScope(Dispatchers.IO).async {
            tripDao.getAllTrip()
        }

    fun getAllTripIdAsync():Deferred<List<Long>> =
        CoroutineScope(Dispatchers.IO).async {
            tripDao.getAllTripId()
        }

    fun getTripWithPlansAsync(tripId: Int): Deferred<LiveData<TripWithPlans>> =
        CoroutineScope(Dispatchers.IO).async {
            tripDao.getTripWithPlans(tripId)
        }

}