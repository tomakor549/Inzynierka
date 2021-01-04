package com.example.inzynierka.room.article

import android.app.Application
import kotlinx.coroutines.*

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

    //dodawanie elementów jak nie istnieją
    fun insertArticle(articles: List<Article>) = CoroutineScope(Dispatchers.IO).launch {
        articleUserDao.insertArticles(articles)
        articleSourceDao.insertArticles(articles)
    }

    //baza danych użytkownika
    fun insertUserArticle(articles: List<Article>) = CoroutineScope(Dispatchers.IO).launch {
        articleUserDao.insertArticles(articles)
    }

    fun updateUserArticle(article: Article) = CoroutineScope(Dispatchers.IO).launch {
        articleUserDao.updateArticle(article)
    }

    fun getUserArticleAsync(articleId: Int): Deferred<Article> =
        CoroutineScope(Dispatchers.IO).async {
        articleUserDao.getArticleById(articleId)
    }

    fun getAllUserArticleAsync() : Deferred<List<Article>> =
    CoroutineScope(Dispatchers.IO).async {
        articleUserDao.getAllArticle()
    }

    fun getAllUserSectionArticleAsync(section: String) : Deferred<List<Article>> =
        CoroutineScope(Dispatchers.IO).async {
        articleUserDao.getAllSectionArticle(section)
    }

    //baza danych aplikacji
    fun insertSourceArticle(articles: List<Article>) = CoroutineScope(Dispatchers.IO).launch {
        articleSourceDao.insertArticles(articles)
    }

    fun updateSourceArticle(article: Article) = CoroutineScope(Dispatchers.IO).launch {
        articleSourceDao.updateArticle(article)
    }

    fun getSourceArticleAsync(articleId: Int) : Deferred<Article> =
        CoroutineScope(Dispatchers.IO).async {
        articleSourceDao.getArticleById(articleId)
    }

    fun getAllSourceArticleAsync() : Deferred<List<Article>> =
        CoroutineScope(Dispatchers.IO).async {
        articleSourceDao.getAllArticle()
    }

    fun getAllSourceSectionArticleAsync(section: String) : Deferred<List<Article>> =
        CoroutineScope(Dispatchers.IO).async {
        articleSourceDao.getAllSectionArticle(section)
    }
}