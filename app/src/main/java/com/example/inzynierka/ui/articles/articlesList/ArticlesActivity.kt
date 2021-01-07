package com.example.inzynierka.ui.articles.articlesList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import com.example.inzynierka.User
import com.example.inzynierka.enum.ArticleNameEnum
import com.example.inzynierka.room.article.Article
import com.example.inzynierka.ui.articles.articlesList.article.ArticleActivity
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.activity_articles.*
import java.io.*
import java.lang.NullPointerException


class ArticlesActivity : AppCompatActivity() {
    private lateinit var articlesActivityViewModel: ArticlesActivityViewModel
    private lateinit var listOfArticle: LiveData<List<Article>>
    private lateinit var toolbar: Toolbar
    private lateinit var searchBar: MaterialSearchBar
    private lateinit var choice: String
    private lateinit var arrayAdapter: ArrayAdapter<*>


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
            ArticleNameEnum.CITY.section ->{
                addToolbar(ArticleNameEnum.CITY.section)
                try {
                    click(ArticleNameEnum.CITY)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono pliku z danymi",Toast.LENGTH_SHORT).show()
                }
            }
            ArticleNameEnum.MOUNTAIN.section ->{
                addToolbar(ArticleNameEnum.MOUNTAIN.section)
                try {
                    click(ArticleNameEnum.MOUNTAIN)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono danych",Toast.LENGTH_SHORT).show()
                }
            }
            ArticleNameEnum.WATER.section -> {
                addToolbar(ArticleNameEnum.WATER.section)
                try {
                    click(ArticleNameEnum.WATER)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono danych",Toast.LENGTH_SHORT).show()
                }
            }
            ArticleNameEnum.FOREST.section ->{
                addToolbar(ArticleNameEnum.FOREST.section)
                try {
                    click(ArticleNameEnum.FOREST)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono danych",Toast.LENGTH_SHORT).show()
                }
            }
            else->{
                Toast.makeText(applicationContext, "Nie znaleziono danych dla tego działu", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun click(selectedSection: ArticleNameEnum){

        listOfArticle = articlesActivityViewModel.getUserArticle(selectedSection)

        val titleList = Transformations.map(listOfArticle) { list ->
            list.map { item -> item.title
            }
        }

        //adapter listy
        val listView: ListView = listView_article
        //uzupełnienie listy w xml o tytuły
        titleList.observe(this, Observer {
            if(it.isNotEmpty()){
                arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, it)
                listView.adapter = arrayAdapter
            }
        })

        //wybór tytułu
        listView.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this, ArticleActivity::class.java)
            var article: Article? = null
            try {
                article = titleList.value?.get(i)?.let { findArticle(it, listOfArticle.value!!) }
            }
            catch (e: NullPointerException){
                Log.i("ArticleActivity", "błąd z wczytaniem artykułu z listOfArticle")
            }
            if (article != null) {
                intent.putExtra("articleId", article.articleId)
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

    private fun findArticle(title: String, articles: List<Article>): Article?{
        for(article in articles){
            if(article.title==title){
                return article
            }
        }
        return null
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