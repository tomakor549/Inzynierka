package com.example.inzynierka.ui.article.articleList

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import kotlinx.android.synthetic.main.activity_article.*


class ArticleActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var viewModel: ArticleActivityViewModel
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val db =
            openOrCreateDatabase("C:\\Users\\Admin\\StudioProjects\\Inzynierka\\app\\src\\main\\res\\raw\\article", Context.MODE_PRIVATE, null)

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(ArticleActivityViewModel::class.java)

        //textView.text = dane.value[0].title

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

        //lista
        val arrayAdapter: ArrayAdapter<*>
        val listData = getNameList()
        if(listData!=null) {
            // dostęp do listy w xml
            val listView = findViewById<ListView>(R.id.listView_article)
            arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, listData!!)
            listView.adapter = arrayAdapter

            listView.setOnItemClickListener { _, _, i, _ ->
                Toast.makeText(this, "Twój wybór to: " + listData[i], Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    fun getNameList(): ArrayList<String>? {

        var list = ArrayList<String>()

        try {
            var c: Cursor? = null
            c = db.rawQuery("select * from 'sea'", null)
            if(c.count==0)
                return null

            while(c.moveToNext()){
                list.add(c.getString(0)+"\n")
            }
            c.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
}