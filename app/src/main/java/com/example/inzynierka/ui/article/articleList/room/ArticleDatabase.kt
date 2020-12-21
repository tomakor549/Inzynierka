package com.example.inzynierka.ui.article.articleList.room

import android.content.Context
import android.content.res.Resources
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inzynierka.R
import java.io.File

@Database(entities = [Article::class], version = 1, exportSchema = true)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object{

        fun getInstance(context: Context): ArticleDao {
            return Room.databaseBuilder(context,
                ArticleDatabase::class.java,
                "article_table")
                .fallbackToDestructiveMigration()
                .createFromFile(File("C:\\Users\\Admin\\StudioProjects\\Inzynierka\\app\\src\\main\\res\\raw\\article.db"))
                .build()
                .articleDao()
        }
    }
}