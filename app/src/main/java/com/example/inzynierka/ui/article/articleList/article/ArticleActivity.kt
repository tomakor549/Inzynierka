package com.example.inzynierka.ui.article.articleList.article

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import com.example.inzynierka.R
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.activity_articles.*

class ArticleActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        addToolbar()

        val extras = intent.extras
        if (extras != null) {
            activity_article_title.text = intent.getStringExtra("title")
            activity_article_website.text = intent.getStringExtra("website")
            activity_article_phone.text = intent.getStringExtra("phoneNumber")
            activity_article_description.text = intent.getStringExtra("description")

            activity_article_phone.paintFlags = Paint.UNDERLINE_TEXT_FLAG
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
}