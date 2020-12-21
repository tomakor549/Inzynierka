package com.example.inzynierka.ui.article.articleList.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Update
    suspend fun update(article: Article)

    @Query("DELETE FROM article_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM article_table")
    fun getAllArticle(): List<Article>

    @Query("SELECT title FROM article_table")
    fun getAllTitle(): List<String>
}