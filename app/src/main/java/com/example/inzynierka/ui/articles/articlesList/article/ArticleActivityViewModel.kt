package com.example.inzynierka.ui.articles.articlesList.article

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inzynierka.enum.ArticleNameEnum
import com.example.inzynierka.room.article.Article
import com.example.inzynierka.room.article.ArticleRepository
import kotlinx.coroutines.runBlocking

class ArticleActivityViewModel(application: Application): AndroidViewModel(application) {

    private var articleRepository: ArticleRepository =
        ArticleRepository(application)

    fun getArticle(articleId: Long): Article = runBlocking {
        articleRepository.getUserArticleAsync(articleId).await()
    }

    fun updateArticle(article: Article){
        articleRepository.updateUserArticle(article)
    }

}