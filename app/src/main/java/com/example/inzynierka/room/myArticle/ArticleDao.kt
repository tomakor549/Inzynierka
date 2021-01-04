package com.example.inzynierka.room.myArticle

import androidx.room.*

interface ArticleDao {

    @Update
    fun updateArticle(article: Article)

    @Insert
    fun insertArticle(articles: List<Article>)

    @Transaction
    @Query("SELECT * FROM article")
    fun getAllArticle(): List<Article>

    @Transaction
    @Query("SELECT * FROM article WHERE articleId = :articleId")
    fun getArticleById(articleId: Article): Article

    @Transaction
    @Query("SELECT * FROM article WHERE section = :section")
    fun getAllSectionArticle(section: Int): List<Article>


}