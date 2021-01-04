package com.example.inzynierka.room.article

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Article::class], version = 2, exportSchema = true)
abstract class ArticleDatabaseUser: RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object{
        @Volatile
        private var instance: ArticleDatabaseUser? = null

        fun getInstance(context: Context): ArticleDatabaseUser?{
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    ArticleDatabaseUser::class.java,
                    "articles_user_db")
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