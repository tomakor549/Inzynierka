package com.example.inzynierka.ui.articles.articlesList.article

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import com.example.inzynierka.R
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        val title: String

        val extras = intent.extras
        if (extras != null) {
            activity_article_title.text = intent.getStringExtra("title")
            activity_article_website.text = intent.getStringExtra("website")
            activity_article_phone.text = intent.getStringExtra("phoneNumber")
            activity_article_description.text = intent.getStringExtra("description")

            activity_article_phone.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        }

        addToolbar(activity_article_title.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun addToolbar(name: String){
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
}