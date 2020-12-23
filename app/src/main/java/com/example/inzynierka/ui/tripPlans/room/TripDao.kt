package com.example.inzynierka.ui.tripPlans.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao {

    @Insert
    fun insert(trip:Trip)

    @Update
    fun update(trip:Trip)

    @Delete
    fun delete(trip:Trip)

    @Delete
    fun delete(trips: List<Trip>)

    @Query("SELECT * FROM trip")
    fun getAllTrip(): LiveData<List<Trip>>

    @Query("SELECT * FROM trip WHERE idName=:value")
    fun getIdNameTrip(value:Int): LiveData<List<Trip>>
}