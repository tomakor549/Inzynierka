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
            tripDao.insert(trip)
        }

    fun updateTrip(trip: Trip) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.update(trip)
        }

    fun deleteTrip(trip: Trip) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.delete(trip)
        }

    fun deleteTripsList(trips: List<Trip>) =
        CoroutineScope(Dispatchers.IO).launch {
            tripDao.delete(trips)
        }

    fun getAllTripAsync():Deferred<LiveData<List<Trip>>> =
        CoroutineScope(Dispatchers.IO).async {
            tripDao.getAllTrip()
        }
    fun getIdNameTrip(value:Int): Deferred<LiveData<List<Trip>>> =
        CoroutineScope(Dispatchers.IO).async {
            tripDao.getAllTrip()
        }

}