package com.example.inzynierka.ui.articles.articlesList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.inzynierka.enum.ArticleNameEnum
import com.example.inzynierka.room.article.Article
import com.example.inzynierka.room.article.ArticleRepository
import com.example.inzynierka.room.trip.Trip
import com.example.inzynierka.room.trip.TripRepository
import kotlinx.coroutines.runBlocking

class ArticlesActivityViewModel(application: Application): AndroidViewModel(application) {

    private var articleRepository: ArticleRepository =
        ArticleRepository(application)

    fun getUserArticle(section: ArticleNameEnum): LiveData<List<Article>> = runBlocking {
        articleRepository.getAllUserSectionArticleAsync(section.name).await()
    }

}