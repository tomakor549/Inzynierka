package com.example.inzynierka.ui.articles


class Article(string: String) {
    private lateinit var title: String
    private lateinit var phoneNumber: String
    private lateinit var website: String
    private lateinit var description: String

    init {
        val delim = "\n"
        val dataList = string.split(delim)
        if(dataList.size>=4){
            title = dataList[0]
            phoneNumber = dataList[1]
            website = dataList[2]
            description = dataList[3]
        }
    }

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