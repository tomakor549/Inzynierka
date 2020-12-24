package com.example.inzynierka.ui.tripPlans.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip")
data class Trip(@PrimaryKey val tripName: String,
                val data: String,)