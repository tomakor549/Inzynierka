package com.example.inzynierka.ui.article

import android.os.Bundle
import android.view.Menu
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.inzynierka.R
import kotlinx.android.synthetic.main.activity_article.*


class ArticleActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

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
        val listData = ArrayList<String>()
        listData.add("eee tam")
        listData.add("inne ee tam")

        // dostęp do listy w xml
        val listView = findViewById<ListView>(R.id.listView_article)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, listData)
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
}