package com.example.inzynierka.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plan")
data class Plan(val tripId: Long,
                var day: Int,
                var description: String){
    @PrimaryKey(autoGenerate = true)
    var planId: Long = 0
}