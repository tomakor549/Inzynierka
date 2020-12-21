package com.example.inzynierka.ui.article.articleList.room

import android.content.Context
import android.content.res.Resources
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inzynierka.R
import java.io.File
import java.io.InputStream

@Database(entities = [Article::class], version = 1, exportSchema = true)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object{

        fun getInstance(context: Context): ArticleDao {
            var inputStream: InputStream = context.resources.openRawResource(R.raw.article)
            return Room.databaseBuilder(context,
                ArticleDatabase::class.java,
                "article_table")
                .fallbackToDestructiveMigration()
                .build()
                .articleDao()
        }
    }
}