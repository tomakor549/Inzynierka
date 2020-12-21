package com.example.inzynierka.ui.article.articleList

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.inzynierka.R
import com.example.inzynierka.ui.article.articleList.room.ArticleDao
import com.example.inzynierka.ui.article.articleList.room.ArticleDatabase
import kotlinx.android.synthetic.main.activity_article.*
import java.io.*
import java.util.logging.Level.INFO


class ArticleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ArticleActivity"
    }

    private lateinit var toolbar: Toolbar
    //private lateinit var viewModel: ArticleActivityViewModel
    private val dao: ArticleDao
        get() = ArticleDatabase.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        /*viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(ArticleActivityViewModel::class.java)*/

        addToolbar()

        readFromFile(R.raw.miasto)

        //lista
        val arrayAdapter: ArrayAdapter<*>
        // dostęp do listy w xml
        val listData = ArrayList<String>()
        listData.add("bbbbb")
        val listView = findViewById<ListView>(R.id.listView_article)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, listData!!)
        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { _, _, i, _ ->
            Toast.makeText(this, "Twój wybór to: " + listData[i], Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun addToolbar(){
        // Dodawanie toolbara
        toolbar = toolbar_article as Toolbar
        toolbar.title = intent.getStringExtra("title")
        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun readFromFile(@RawRes section: Int){

        var string: String? = ""
        val stringBuilder = StringBuilder()

        var inputStream: InputStream = resources.openRawResource(section)
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

        var a = stringBuilder.toString()
    }
}