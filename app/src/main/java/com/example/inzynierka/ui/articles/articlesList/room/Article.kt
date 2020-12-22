package com.example.inzynierka.ui.articles.articlesList.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
data class Article (@PrimaryKey var id: Int,
                    var title: String,
                    var webpage: String,
                    var phone: String,
                    var description: String){
}