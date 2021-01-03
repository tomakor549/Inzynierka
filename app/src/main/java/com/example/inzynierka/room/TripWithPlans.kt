package com.example.inzynierka.room

import androidx.room.Embedded
import androidx.room.Relation

data class TripWithPlans (
    @Embedded val trip: Trip,
    @Relation(
        parentColumn = "tripId",
        entityColumn = "tripId"
    )
    val plans: List<Plan>
)