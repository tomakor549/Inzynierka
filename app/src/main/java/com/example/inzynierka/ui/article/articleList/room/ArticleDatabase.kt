package com.example.inzynierka.ui.article.articleList.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

@Database(entities = [Article::class], version = 1, exportSchema = true)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object{
        private var instance: ArticleDatabase? = null

        fun getInstance(context: Context): ArticleDatabase? {
            if(instance ==null){
                instance = Room.databaseBuilder(context,
                    ArticleDatabase::class.java,
                    "article_table")
                    .fallbackToDestructiveMigration()
                    .createFromFile(File("res/raw/article.db"))
                    .build()
            }
            return instance
        }

        fun deleteInstanceOfDatabase(){
            instance =null
        }
    }
}