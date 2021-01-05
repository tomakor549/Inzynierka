package com.example.inzynierka.ui.articles.articlesList.article

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import com.example.inzynierka.room.article.Article
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var articleActivityViewModel: ArticleActivityViewModel
    private lateinit var phoneNumberEditText: EditText
    private lateinit var titleEditText: EditText
    private lateinit var websiteEditText: EditText
    private lateinit var descEditText: EditText
    private lateinit var article: Article

    private var articleId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        articleActivityViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(ArticleActivityViewModel::class.java)

        phoneNumberEditText = activity_article_phone_edit
        titleEditText = activity_article_title_edit
        websiteEditText = activity_article_website_edit
        descEditText = activity_article_description_edit

        val extras = intent.extras
        if (extras != null) {
            try {
                articleId = intent.getLongExtra("articleId", -1)
            }
            catch (e: Exception){
                Log.i("ArticleActivity", "dane są nullami")
                onBackPressed()
            }
        }else{
            onBackPressed()
        }

        article = articleActivityViewModel.getArticle(articleId)

        phoneNumberEditText.setText(article.phoneNumber)
        titleEditText.setText(article.title)
        websiteEditText.setText(article.website)
        descEditText.setText(article.description)

        addToolbar()
        setButtons()
    }

    private fun setButtons(){
        val focusable = false
        val isFocusableInTouchMode = true

        activity_article_edit_button.setOnClickListener {
            activity_article_confirm_button.visibility = View.VISIBLE
            activity_article_edit_button.visibility = View.GONE
            activity_article_default_button.visibility = View.GONE

            editableEditText(android.R.drawable.edit_text, focusable, isFocusableInTouchMode)
        }

        activity_article_confirm_button.setOnClickListener {
            activity_article_confirm_button.visibility = View.GONE
            activity_article_edit_button.visibility = View.VISIBLE
            activity_article_default_button.visibility = View.VISIBLE

            editableEditText(R.color.transparent, !focusable, !isFocusableInTouchMode)
        }


    }

    private fun editableEditText(backgroundEditText: Int, focusable: Boolean, isFocusableInTouchMode: Boolean){
        titleEditText.setBackgroundResource(backgroundEditText)
        titleEditText.isFocusable = focusable
        titleEditText.isFocusableInTouchMode = isFocusableInTouchMode
        websiteEditText.setBackgroundResource(backgroundEditText)
        websiteEditText.isFocusable = focusable
        websiteEditText.isFocusableInTouchMode = isFocusableInTouchMode
        descEditText.setBackgroundResource(backgroundEditText)
        descEditText.isFocusable = focusable
        descEditText.isFocusableInTouchMode = isFocusableInTouchMode
        phoneNumberEditText.setBackgroundResource(backgroundEditText)
        phoneNumberEditText.isFocusable = focusable
        phoneNumberEditText.isFocusableInTouchMode = isFocusableInTouchMode
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // dodanie menu
        menuInflater.inflate(R.menu.toolbar_sharing_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id==R.id.action_emergency){
            callEmergency()
            return true
        }

        if(id==R.id.action_sharing){
            val articleTitle = titleEditText.text.toString()
            val desc = descEditText.text.toString()
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, articleTitle)
                putExtra(Intent.EXTRA_SUBJECT, articleTitle)
                putExtra(Intent.EXTRA_TEXT, desc)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
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