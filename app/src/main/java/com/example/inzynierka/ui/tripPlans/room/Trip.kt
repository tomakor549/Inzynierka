package com.example.inzynierka.ui.tripPlans.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip")
data class Trip(val tripName: String,
                @PrimaryKey val tripId: Long,
                val startDate: String)