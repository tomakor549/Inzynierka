package com.example.inzynierka.ui.article.articleList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.inzynierka.ui.article.articleList.room.Article
import com.example.inzynierka.ui.article.articleList.room.ArticleRepository
import kotlinx.coroutines.*

class ArticleActivityViewModel(application: Application): AndroidViewModel(application) {
/*
    private var articleRepository: ArticleRepository = ArticleRepository(application)
    private var allArticle: Deferred<LiveData<List<Article>>> = articleRepository.getAllArticle()

    fun getAllArticle(): LiveData<List<Article>> = runBlocking {
        allArticle.await()
    }

 */
}