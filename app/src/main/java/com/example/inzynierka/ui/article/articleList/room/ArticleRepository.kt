package com.example.inzynierka.ui.article.articleList.room

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ArticleRepository(application: Application) {

    private var articleDao: ArticleDao

    init {
        val database =
            ArticleDatabase.getInstance(
                application.applicationContext
            )

        articleDao = database!!.articleDao()
    }

    fun getAllCityArticle(): Deferred<LiveData<List<Article>>> =
        CoroutineScope(Dispatchers.IO).async {
            articleDao.getAllCityArticle()
        }
}