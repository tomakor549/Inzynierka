package com.example.inzynierka.room.myArticle

import android.app.Application
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleRepository(application: Application) {
    private var articleSourceDao: ArticleDao
    private var articleUserDao: ArticleDao

    init {
        val sourceDatabase =
            ArticleDatabaseSource.getInstance(
                application.applicationContext
            )
        val userDatabase =
            ArticleDatabaseUser.getInstance(
                application.applicationContext
            )

        articleSourceDao = sourceDatabase!!.articleDao()
        articleUserDao = userDatabase!!.articleDao()
    }

    //baza danych użytkownika
    fun insertUserArticle(articles: List<Article>) = CoroutineScope(Dispatchers.IO).launch {
        articleUserDao.insertArticle(articles)
    }

    fun updateUserArticle(article: Article) = CoroutineScope(Dispatchers.IO).launch {
        articleUserDao.updateArticle(article)
    }

    fun getUserArticle(articleId: Article) = CoroutineScope(Dispatchers.IO).launch {
        articleUserDao.getArticleById(articleId)
    }

    fun getAllUserArticle()= CoroutineScope(Dispatchers.IO).launch {
        articleUserDao.getAllArticle()
    }

    fun getAllUserSectionArticle(section: Int) = CoroutineScope(Dispatchers.IO).launch {
        articleUserDao.getAllSectionArticle(section)
    }

    //baza danych aplikacji
    fun insertSourceArticle(articles: List<Article>) = CoroutineScope(Dispatchers.IO).launch {
        articleSourceDao.insertArticle(articles)
    }

    fun updateSourceArticle(article: Article) = CoroutineScope(Dispatchers.IO).launch {
        articleSourceDao.updateArticle(article)
    }

    fun getSourceArticle(articleId: Article) = CoroutineScope(Dispatchers.IO).launch {
        articleSourceDao.getArticleById(articleId)
    }

    fun getAllSourceArticle()= CoroutineScope(Dispatchers.IO).launch {
        articleSourceDao.getAllArticle()
    }

    fun getAllSourceSectionArticle(section: Int) = CoroutineScope(Dispatchers.IO).launch {
        articleSourceDao.getAllSectionArticle(section)
    }
}