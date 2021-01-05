package com.example.inzynierka.room.article

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Update
    fun updateArticle(article: Article)

    @Insert
    fun insertArticles(articles: List<Article>)

    /*
    @Transaction
    @Query("SELECT * FROM article")
    fun getAllArticle(): List<Article>
    */
    @Transaction
    @Query("SELECT * FROM article WHERE articleId = :articleId")
    fun getArticleById(articleId: Long): Article

    @Transaction
    @Query("SELECT * FROM article WHERE section = :section")
    fun getAllSectionArticle(section: String): LiveData<List<Article>>

    @Transaction
    @Query("SELECT count(*) FROM article")
    fun getAllArticleNumber(): Long


}