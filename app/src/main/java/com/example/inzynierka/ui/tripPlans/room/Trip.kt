package com.example.inzynierka.ui.tripPlans.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip")
data class Trip(val name: String,
                val idName: String,
                val data: String,
                val time: String,
                val description: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}