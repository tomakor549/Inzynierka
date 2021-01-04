package com.example.inzynierka.room.trip

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Trip::class, Plan::class], version = 4, exportSchema = true)
abstract class TripDatabase: RoomDatabase(){
    abstract fun tripDao(): TripDao

    companion object{
        @Volatile
        private var instance: TripDatabase? = null

        fun getInstance(context: Context): TripDatabase?{
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    TripDatabase::class.java,
                    "trip_plans_db")
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