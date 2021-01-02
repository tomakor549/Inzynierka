package com.example.inzynierka.ui.articles.articlesList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import java.lang.Exception


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


        when(choice){
            ArticleNameEnum.CITY.toString() ->{
                addToolbar("Tereny miejsckie")
                try {
                    click(R.raw.city)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono pliku z danymi",Toast.LENGTH_SHORT).show()
                }
            }
            ArticleNameEnum.MOUNTAIN.toString() ->{
                addToolbar("Tereny górskie")
                try {
                    click(R.raw.mountain)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono pliku z danymi",Toast.LENGTH_SHORT).show()
                }
            }
            ArticleNameEnum.SEA.toString() -> {
                addToolbar("Akweny")
                try {
                    click(R.raw.water)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono pliku z danymi",Toast.LENGTH_SHORT).show()
                }
            }
            ArticleNameEnum.FOREST.toString() ->{
                addToolbar("Tereny leśne")
                try {
                    click(R.raw.forest)
                }catch (ex: FileNotFoundException){
                    Toast.makeText(this,"Nie znaleziono pliku z danymi",Toast.LENGTH_SHORT).show()
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

        val listData = getList(section)
        if(listData==null){
            Toast.makeText(this,"Brak danych",Toast.LENGTH_SHORT).show()
            return
        }
        //lista tytułów artykułów
        val titleList = ArrayList<String>()
        //wczytanie danych do listy tytułów
        for(element in listData!!){
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
            val article = findArticle(titleList[i], listData!!)
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

    private fun findArticle(title: String, articles:List<Article>): Article?{
        for(article in articles){
            if(article.getTitle()==title){
                return article
            }
        }
        return null
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

    private fun getList(@RawRes section: Int): List<Article>?{
        val string = readFromFile(section)
        val delim = "***\n"

        if(string.isBlank() || string.length<5){
            return null
        }
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

}