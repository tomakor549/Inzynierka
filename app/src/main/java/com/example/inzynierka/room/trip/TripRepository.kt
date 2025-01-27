package com.example.inzynierka.room.trip

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class TripRepository(application: Application) {
    private var tripDao: TripDao

    init {
        val database =
            TripDatabase.getInstance(
                application.applicationContext
            )

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

    fun insertPlans(plans: List<Plan>) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.insertPlans(plans)
        }

    fun updateTrip(trip: Trip) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.updateTrip(trip)
        }

    fun updatePlan(plan: Plan) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.updatePlan(plan)
        }

    fun updatePlans(plans: List<Plan>) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.updatePlans(plans)
        }

    fun deleteTrip(trip: Trip) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.deleteTrip(trip)
        }

    fun deleteTrip(plan: Plan) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.deletePlan(plan)
        }

    fun deleteTripById(tripId: Long) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.deleteTripByID(tripId)
        }

    fun deletePlansById(tripId: Long) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.deletePlansByTripID(tripId)
        }

    fun getAllTripAsync():Deferred<LiveData<List<Trip>>> =
        CoroutineScope(Dispatchers.IO).async {
            tripDao.getAllTrip()
        }

    fun getAllTripIdAsync():Deferred<List<Long>> =
        CoroutineScope(Dispatchers.IO).async {
            tripDao.getAllTripId()
        }

    fun getTripWithPlansAsync(tripId: Long): Deferred<TripWithPlans> =
        CoroutineScope(Dispatchers.IO).async {
            tripDao.getTripWithPlans(tripId)
        }

}