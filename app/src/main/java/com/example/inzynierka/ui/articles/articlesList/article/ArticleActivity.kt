package com.example.inzynierka.ui.articles.articlesList.article

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
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

        val loadArticle: Article? = articleActivityViewModel.getUserArticle(articleId)

        if(loadArticle==null){
            Log.i("ArticleActivity", "nie znaleziono artykułu")
            Toast.makeText(this, "Nie znaleziono danych", Toast.LENGTH_SHORT).show()
            onBackPressed()
            return
        }
        article = loadArticle


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

        //Edycja artykułu
        activity_article_edit_button.setOnClickListener {
            editData()
            editableEditText(android.R.drawable.edit_text, focusable, isFocusableInTouchMode)
        }

        //zapisanine zmian
        activity_article_confirm_button.setOnClickListener {
            editableEditText(R.color.transparent, !focusable, !isFocusableInTouchMode)
            article.title = titleEditText.text.toString()
            article.phoneNumber = phoneNumberEditText.text.toString()
            article.website = websiteEditText.text.toString()
            article.description = descEditText.text.toString()
            articleActivityViewModel.updateArticle(article)
            confirmDialogBuilder("zmiany wprowadzono pomyślnie")

            viewingData()
        }

        //przywrócenie artykułu domyślnego
        activity_article_default_button.setOnClickListener {
            val positiveClick = {
                val newArticle: Article? = articleActivityViewModel.getSourceArticle(articleId)
                if (newArticle != null) {
                    article = newArticle
                    articleActivityViewModel.updateArticle(article)
                    updateEditText()
                } else
                    Toast.makeText(this, "Nie znaleziono danych źródłowych", Toast.LENGTH_SHORT).show()

                viewingData()
            }

            changeArticle("Przywrócenie danych",
                "Na pewno chcesz przywrócić stare dane? Po potwierdzeniu aktualne dane zostaną zastąpione.",
                positiveClick)
        }

        //odrzucenie zmian edycji
        activity_article_discard_button.setOnClickListener {
            val positiveClick = {
                activity_article_confirm_button.visibility = View.GONE
                activity_article_discard_button.visibility = View.GONE
                activity_article_edit_button.visibility = View.VISIBLE
                activity_article_default_button.visibility = View.VISIBLE
                viewingData()
                updateEditText()
                editableEditText(R.color.transparent, !focusable, !isFocusableInTouchMode)
            }

            changeArticle("Anulowanie zmian",
                "Na pewno chcesz anulować wprowadzone zmiany?",
                positiveClick)
        }
    }

    private fun editData(){
        activity_article_confirm_button.visibility = View.VISIBLE
        activity_article_discard_button.visibility = View.VISIBLE
        activity_article_edit_button.visibility = View.GONE
        activity_article_default_button.visibility = View.GONE
        enabledEditText(true)
    }

    private fun viewingData(){
        activity_article_confirm_button.visibility = View.GONE
        activity_article_discard_button.visibility = View.GONE
        activity_article_edit_button.visibility = View.VISIBLE
        activity_article_default_button.visibility = View.VISIBLE
        enabledEditText(false)
    }

    private fun changeArticle(titleMessage: String, message: String, positiveClick: () -> Unit){
        val builder = AlertDialog.Builder(this)

        builder.setTitle(titleMessage)
        builder.setMessage(message)

        builder.setPositiveButton("Tak") { _, _ ->
            positiveClick()
        }
        builder.setNegativeButton("Nie") { _, _ ->

        }
        builder.show()
    }

    private fun confirmDialogBuilder(message: String){
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Potwierdzanie zmian")
        builder.setMessage(message)

        builder.setPositiveButton("Ok") { _, _ ->

        }
        builder.show()
    }

    private fun updateEditText(){
        phoneNumberEditText.setText(article.phoneNumber)
        titleEditText.setText(article.title)
        websiteEditText.setText(article.website)
        descEditText.setText(article.description)
    }

    private fun enabledEditText(enabled: Boolean){
        titleEditText.isEnabled = enabled
        websiteEditText.isEnabled = enabled
        descEditText.isEnabled = enabled
        phoneNumberEditText.isEnabled = enabled
    }

    private fun editableEditText(backgroundEditText: Int, focusable: Boolean, isFocusableInTouchMode: Boolean){
        if(android.R.drawable.edit_text == backgroundEditText)
            titleEditText.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        else
            titleEditText.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
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