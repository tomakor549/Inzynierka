package com.example.inzynierka.ui.articles.articlesList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.inzynierka.enum.ArticleNameEnum
import com.example.inzynierka.room.article.Article
import com.example.inzynierka.room.article.ArticleRepository
import com.example.inzynierka.room.trip.Trip
import com.example.inzynierka.room.trip.TripRepository
import kotlinx.coroutines.runBlocking

class ArticlesActivityViewModel(application: Application): AndroidViewModel(application) {

    private var articleRepository: ArticleRepository =
        ArticleRepository(application)

    fun getSourceArticle(section: ArticleNameEnum): List<Article> = runBlocking {
        articleRepository.getAllSourceSectionArticleAsync(section.name).await()
    }

    fun getUserArticle(section: ArticleNameEnum): List<Article> = runBlocking {
        articleRepository.getAllUserSectionArticleAsync(section.name).await()
    }

    fun insertSourceArticle(list: List<Article>){
        articleRepository.insertArticle(list)
    }

    fun stringToArticle(string: String, section: ArticleNameEnum):Article?{
        var article: Article? = null
        val delim = "\n"
        val dataList = string.split(delim)
        if(dataList.size>=4){
            article = Article(dataList[0], dataList[1], dataList[2], dataList[3], section.name)
        }
        return article
    }
}