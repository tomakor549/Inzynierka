package com.example.inzynierka.ui.articles.articlesList

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.inzynierka.R
import com.example.inzynierka.ui.articles.Article
import com.example.inzynierka.ui.articles.articlesList.article.ArticleActivity
import com.example.inzynierka.ui.articles.enum.ArticleNameEnum
import kotlinx.android.synthetic.main.activity_articles.*
import java.io.*


class ArticlesActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var choice: String

    //private lateinit var viewModel: ArticleActivityViewModel

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)

        /*viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(ArticleActivityViewModel::class.java)*/

        choice = intent.getStringExtra("title")

        addToolbar()

        when(choice){
            ArticleNameEnum.CITY.toString() ->
                loadList(R.raw.miasto)
            ArticleNameEnum.SEA.toString() ->
                loadList(R.raw.akweny)
            else->{
                Toast.makeText(applicationContext, "Nie znaleziono danych dla tego działu", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun loadList(@RawRes section: Int){

        //adapter listy
        val arrayAdapter: ArrayAdapter<*>

        val listData = getList(section)
        //lista tytułów artykułów
        val titleList = ArrayList<String>()
        //wczytanie danych do listy tytułów
        for(element in listData){
            titleList.add(element.getTitle())
        }

        //uzupełnienie listy w xml o tytuły
        val listView: ListView = listView_article
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, titleList)
        listView.adapter = arrayAdapter

        //wybór tytułu
        listView.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this, ArticleActivity::class.java)
            val article = findArticle(titleList[i], listData)
            if (article != null) {
                intent.putExtra("title", article.getTitle())
                intent.putExtra("website", article.getWebsite())
                intent.putExtra("phoneNumber", article.getPhoneNumber())
                intent.putExtra("description", article.getDescription())
                startActivity(intent)
            }else{
                Toast.makeText(this, "Brak informacji o artykule", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun findArticle(title: String, articles:List<Article>): Article?{
        for(article in articles){
            if(article.getTitle()==title){
                return article
            }
        }
        return null
    }

    private fun addToolbar(){
        // Dodawanie toolbara
        toolbar = toolbar_articles as Toolbar
        toolbar.title = choice
        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getList(@RawRes section: Int): List<Article>{
        val string = readFromFile(section)
        val delim = "***\n"

        val dataList = string.split(delim)
        val articles = ArrayList<Article>()
        for(element in dataList){
            articles.add(Article(element))
        }
        return articles
    }

    private fun readFromFile(@RawRes section: Int): String{

        var string: String? = ""
        val stringBuilder = StringBuilder()

        val inputStream: InputStream = resources.openRawResource(section)
        val buffreader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            try {
                if (buffreader.readLine().also { string = it } == null) break
            } catch (e: IOException) {
                e.printStackTrace()
            }
            stringBuilder.append(string).append("\n")
        }
        inputStream.close()

        return stringBuilder.toString()
    }
}