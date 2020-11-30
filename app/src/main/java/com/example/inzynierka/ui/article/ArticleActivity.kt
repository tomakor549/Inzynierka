package com.example.inzynierka.ui.article

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.inzynierka.R


class ArticleActivity : AppCompatActivity() {
    lateinit var scrollview: ScrollView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        // Dodawanie toolbara
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        // ustawienie toolbara jako actionBar
        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}