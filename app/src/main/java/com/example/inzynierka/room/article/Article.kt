package com.example.inzynierka.room.article

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class Article(var title: String,
                   var phoneNumber: String,
                   var website: String,
                   var description: String,
                   var section: String){
    @PrimaryKey(autoGenerate = true)
    var articleId: Long = 0
}