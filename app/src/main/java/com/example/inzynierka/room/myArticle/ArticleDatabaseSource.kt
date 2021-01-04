package com.example.inzynierka.room.myArticle

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Article::class], version = 1, exportSchema = true)
abstract class ArticleDatabaseSource: RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object{
        @Volatile
        private var instance: ArticleDatabaseSource? = null

        fun getInstance(context: Context): ArticleDatabaseSource?{
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    ArticleDatabaseSource::class.java,
                    "articles_source_db")
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