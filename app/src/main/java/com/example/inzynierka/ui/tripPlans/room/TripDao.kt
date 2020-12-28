package com.example.inzynierka.ui.tripPlans.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao {

    //Insert
    @Insert
    fun insertTrip(trip:Trip)

    @Insert
    fun insertPlan(plan: Plan)

    @Insert
    fun insertPlans(plans: List<Plan>)

    //Update
    @Update
    fun updateTrip(trip:Trip)

    @Update
    fun updatePlan(plan: Plan)

    @Update
    fun updatePlans(plans: List<Plan>)

    //Delete
    @Delete
    fun deleteTrip(trip:Trip)

    @Delete
    fun deletePlan(plan: Plan)

    @Delete
    fun deletePlans(plans: List<Plan>)

    //Function
    @Transaction
    @Query("SELECT * FROM trip")
    fun getAllTrip(): LiveData<List<Trip>>

    @Transaction
    @Query("SELECT tripId FROM trip")
    fun getAllTripId(): List<Long>

    @Transaction
    @Query("SELECT * FROM trip WHERE tripId = :tripId")
    fun getTripWithPlans(tripId: Int): LiveData<TripWithPlans>

    @Transaction
    @Query("DELETE FROM trip WHERE tripId=:id")
    fun deleteTripByID(id: Long)

    @Transaction
    @Query("DELETE FROM `plan` WHERE tripId=:tripId")
    fun deletePlansByTripID(tripId: Long)

}