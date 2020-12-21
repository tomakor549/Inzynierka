package com.example.inzynierka.ui.article.articleList

import java.io.FileDescriptor


class Article {
    private lateinit var title: String
    private lateinit var phoneNumber: String
    private lateinit var website: String
    private lateinit var description: String

    fun setTitle(title: String){
        this.title = title
    }
    fun setPhoneNumber(phoneNumber: String){
        this.phoneNumber = phoneNumber
    }
    fun setWebsite(website: String){
        this.website = website
    }
    fun setDescription(description: String){
        this.description = description
    }

    fun getTitle(): String{
        return title
    }
    fun getPhoneNumber(): String{
        return phoneNumber
    }
    fun getWebsite(): String{
        return website
    }
    fun getDescription(): String{
        return description
    }
}