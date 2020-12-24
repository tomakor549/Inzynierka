package com.example.inzynierka.ui.tripPlans.room

import androidx.room.Embedded
import androidx.room.Relation

data class TripWithPlans (
    @Embedded val trip: Trip,
    @Relation(
        parentColumn = "tripName",
        entityColumn = "tripName"
    )
    val plans: List<Plan>
)