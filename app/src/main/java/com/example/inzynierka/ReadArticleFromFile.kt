package com.example.inzynierka

import android.content.res.Resources
import android.telephony.PhoneNumberUtils
import android.webkit.URLUtil
import androidx.annotation.RawRes
import com.example.inzynierka.enum.ArticleNameEnum
import com.example.inzynierka.room.article.Article
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.*

class ReadArticleFromFile(private val resources: Resources) {
    val articlesList = ArrayList<Article>()

    init {
        readFromFile(R.raw.water, ArticleNameEnum.WATER)
        readFromFile(R.raw.city, ArticleNameEnum.CITY)
        readFromFile(R.raw.mountain, ArticleNameEnum.MOUNTAIN)
        readFromFile(R.raw.forest, ArticleNameEnum.FOREST)
    }

    private fun readFromFile(@RawRes section: Int, articleNameEnum: ArticleNameEnum){

        var articleLine: String? = ""
        val stringBuilder = StringBuilder()

        val inputStream: InputStream = resources.openRawResource(section)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        while (true) {
            try {
                if (bufferedReader.readLine().also {
                        articleLine = it } == null)
                    break
            } catch (e: IOException) {
                e.printStackTrace()
            }
            stringBuilder.append(articleLine).append("\n")
        }
        inputStream.close()

        val allArticleString = stringBuilder.toString()

        val delim = "***\n"

        if(allArticleString.isBlank() || allArticleString.length<5){
            return
        }

        val articlesString = allArticleString.split(delim)

        for(articleString in articlesString){
            if(countLines(articleString)==5){
                val article = stringToArticle(articleString, articleNameEnum)
                if (article != null) {
                    if(article.title.length < 60){
                        articlesList.add(checkArticle(article))
                    }
                }
            }
        }
    }

    private fun checkArticle(article: Article): Article{
        val tmp: String

        //sprawdzenie odwrotności numeru telefonu i strony internetowej
        if(!checkPhoneAndUrl(article.phoneNumber, article.website)){
            if (checkPhoneAndUrl(article.website, article.phoneNumber)){
                tmp = article.phoneNumber
                article.phoneNumber = article.website
                article.website = tmp
            }
        }

        if(article.phoneNumber=="-")
            article.phoneNumber = ""
        if(article.title=="-")
            article.title = ""
        if(article.description=="-")
            article.description = ""
        if(article.website=="-")
            article.website = ""

        return article
    }

    private fun checkPhoneAndUrl(phone: String, website: String): Boolean{

        //sprawdzanie czy to adres strony
        if(URLUtil.isValidUrl(website))
            return true

        if(phone.length<3)
            return false

        //sprawdzanie czy to numer telefonu
        if(PhoneNumberUtils.isGlobalPhoneNumber(phone))
            return true

        return false
    }

    private fun stringToArticle(string: String, section: ArticleNameEnum):Article?{
        var article: Article? = null
        val split = "\n"
        val dataList = string.split(split)
        if(dataList.size>=4){
            article = Article(dataList[0], dataList[1], dataList[2], dataList[3], section.name)
        }
        return article
    }

    private fun countLines(str: String): Int{
        val lines: List<String> = str.split("\n")
        return lines.size
    }
}