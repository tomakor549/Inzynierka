package com.example.inzynierka.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.example.inzynierka.R
import kotlinx.android.synthetic.main.activity_article.*
import androidx.core.view.marginTop as marginTop1

class ArticleActivity : AppCompatActivity() {
    lateinit var scrollview: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        var txt = TextView(this)
        txt.text = "Przykład"
        txt.textSize = 20f


        scrollview = article_activity_scrollView

    }
}