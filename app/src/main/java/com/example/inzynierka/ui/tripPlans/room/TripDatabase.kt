package com.example.inzynierka.ui.tripPlans.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Trip::class], version = 1, exportSchema = true)
abstract class TripDatabase: RoomDatabase(){
    abstract fun tripDao(): TripDao

    companion object{
        private var instance: TripDatabase? = null

        fun getInstance(context: Context): TripDatabase?{
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    TripDatabase::class.java,
                    "trip")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

        fun deleteInstanceOfDatabase(){
            instance = null
        }
    }
}