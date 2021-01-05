package com.example.inzynierka

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.inzynierka.enum.ArticleNameEnum
import com.example.inzynierka.room.article.Article
import com.example.inzynierka.room.article.ArticleRepository
import kotlinx.coroutines.runBlocking

class StartActivityViewModel(application: Application): AndroidViewModel(application) {

    private var articleRepository: ArticleRepository =
        ArticleRepository(application)

    fun getArticleNumber(): Long = runBlocking {
        articleRepository.getAllArticleNumberAsync().await()
    }

    fun getUserArticle(section: ArticleNameEnum): LiveData<List<Article>> = runBlocking {
        articleRepository.getAllUserSectionArticleAsync(section.name).await()
    }

    fun insertArticle(list: List<Article>){
        articleRepository.insertArticle(list)
    }

}