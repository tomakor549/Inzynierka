package com.example.inzynierka.ui.articles.articlesList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import com.example.inzynierka.ui.articles.articlesList.article.ArticleActivity
import com.example.inzynierka.enum.ArticleNameEnum
import com.example.inzynierka.room.article.Article
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.activity_articles.*
import java.io.*


class ArticlesActivity : AppCompatActivity() {
    private lateinit var articlesActivityViewModel: ArticlesActivityViewModel

    private lateinit var toolbar: Toolbar
    private lateinit var searchBar: MaterialSearchBar
    private lateinit var choice: String
    private var selectedSection: ArticleNameEnum? = null

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)

        articlesActivityViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(ArticlesActivityViewModel::class.java)

        searchBar = search_bar
        choice = intent.getStringExtra("title").toString()

        userChoice()

    }

    private fun userChoice(){
        when(choice){
            ArticleNameEnum.CITY.name ->{
                addToolbar(ArticleNameEnum.CITY.name)
                selectedSection = ArticleNameEnum.CITY
                try {
                    click(R.raw.city)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono pliku z danymi",Toast.LENGTH_SHORT).show()
                }
            }
            ArticleNameEnum.MOUNTAIN.name ->{
                addToolbar(ArticleNameEnum.MOUNTAIN.name)
                selectedSection = ArticleNameEnum.MOUNTAIN
                try {
                    click(R.raw.mountain)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono danych",Toast.LENGTH_SHORT).show()
                }
            }
            ArticleNameEnum.SEA.name -> {
                addToolbar(ArticleNameEnum.SEA.name)
                selectedSection = ArticleNameEnum.SEA
                try {
                    click(R.raw.water)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono danych",Toast.LENGTH_SHORT).show()
                }
            }
            ArticleNameEnum.FOREST.name ->{
                addToolbar(ArticleNameEnum.FOREST.name)
                selectedSection = ArticleNameEnum.FOREST
                try {
                    click(R.raw.forest)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono danych",Toast.LENGTH_SHORT).show()
                }
            }
            else->{
                Toast.makeText(applicationContext, "Nie znaleziono danych dla tego działu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun click(@RawRes section: Int){

        //adapter listy
        val arrayAdapter: ArrayAdapter<*>

        var listData = selectedSection?.let { articlesActivityViewModel.getUserArticle(it) }

        if (listData != null) {
            if(listData.isEmpty())
                listData = getList(section)
        }
        else{
            Toast.makeText(this,"Brak danych",Toast.LENGTH_SHORT).show()
            return
        }

        //lista tytułów artykułów
        val titleList = ArrayList<String>()
        //wczytanie danych do listy tytułów
        for(element in listData!!){
            titleList.add(element.title)
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
                intent.putExtra("title", article.title)
                intent.putExtra("website", article.website)
                intent.putExtra("phoneNumber", article.phoneNumber)
                intent.putExtra("description", article.description)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Brak informacji o artykule", Toast.LENGTH_LONG).show()
            }
        }

        searchBar.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                arrayAdapter.filter.filter(p0)
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun findArticle(title: String, articles:List<Article>): Article?{
        for(article in articles){
            if(article.title==title){
                return article
            }
        }
        return null
    }

    private fun getList(@RawRes section: Int): List<Article>? {
        val string = readFromFile(section)
        val delim = "***\n"

        if(string.isBlank() || string.length<5){
            return null
        }
        val dataList = string.split(delim)

        val articles = ArrayList<Article>()
        for(element in dataList){
            if(countLines(element)==5){
                val article = selectedSection?.let { articlesActivityViewModel.stringToArticle(element, it) }
                if (article != null) {
                    if(article.title.length < 60)
                        articles.add(article)
                }
            }
        }
        return articles
    }

    private fun countLines(str: String): Int{
        val lines: List<String> = str.split("\n")
        return lines.size
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // dodanie menu
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id==R.id.action_emergency){
            callEmergency()
            return true
        }

        return false
    }

    private fun callEmergency(){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + "112")
        startActivity(intent)
    }

    private fun addToolbar(name:String){
        // Dodawanie toolbara
        toolbar = toolbar_articles as Toolbar
        toolbar.title = name
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}