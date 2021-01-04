package com.example.inzynierka.room.trip

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip")
data class Trip(val tripName: String,
                @PrimaryKey val tripId: Long,
                val startDate: String,
                val endDate: String)