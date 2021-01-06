package com.example.inzynierka.ui.articles.articlesList.article

import android.app.AlertDialog
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

        updateEditText()
        activePhoneAndWebsiteClick(true)

        addToolbar()
        setButtons()
    }

    private fun setButtons(){
        //Edycja artykułu
        activity_article_edit_button.setOnClickListener {
            editDataEditing(true)
            editableEditText(true)
        }

        //zapisanine zmian
        activity_article_confirm_button.setOnClickListener {
            editDataEditing(false)
            editableEditText(false)
            updateUserArticle()
            Toast.makeText(this, "Zmiany wprowadzono pomyślnie", Toast.LENGTH_SHORT).show()
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

                editDataEditing(false)
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
                editDataEditing(false)
                editableEditText(false)
                updateEditText()
            }

            changeArticle("Anulowanie zmian",
                "Na pewno chcesz anulować wprowadzone zmiany?",
                positiveClick)
        }
    }

    private fun activePhoneAndWebsiteClick(active: Boolean){

        if(!active){
            phoneNumberEditText.setOnClickListener(null)
            websiteEditText.setOnClickListener(null)
        }else{
            phoneNumberEditText.isFocusable = false
            phoneNumberEditText.isFocusableInTouchMode = false
            websiteEditText.isFocusable = false
            websiteEditText.isFocusableInTouchMode = false

            val phoneNumber = phoneNumberEditText.text.toString()
            if(phoneNumber.length>=3){
                if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
                    phoneNumberEditText.setOnClickListener{
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:$phoneNumber")
                        startActivity(intent)
                    }
                }else{
                    phoneNumberEditText.setOnClickListener(null)
                }
            }else{
                phoneNumberEditText.setOnClickListener(null)
            }

            val website = websiteEditText.text.toString()

            if(website.isBlank()){
                websiteEditText.setOnClickListener(null)
                return
            }

            if(URLUtil.isValidUrl(website)){
                websiteEditText.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(website)
                    startActivity(intent)
                }
            }else{
                websiteEditText.setOnClickListener{
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, website)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }
            }
        }
    }

    private fun editDataEditing(editing: Boolean){

        if(editing){
            activePhoneAndWebsiteClick(false)
            activity_article_confirm_button.visibility = View.VISIBLE
            activity_article_discard_button.visibility = View.VISIBLE
            activity_article_edit_button.visibility = View.GONE
            activity_article_default_button.visibility = View.GONE
        }
        else{
            activePhoneAndWebsiteClick(true)
            activity_article_confirm_button.visibility = View.GONE
            activity_article_discard_button.visibility = View.GONE
            activity_article_edit_button.visibility = View.VISIBLE
            activity_article_default_button.visibility = View.VISIBLE
        }
        enabledEditText(editing)
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

    private fun updateEditText(){
        phoneNumberEditText.setText(article.phoneNumber)
        titleEditText.setText(article.title)
        websiteEditText.setText(article.website)
        descEditText.setText(article.description)
    }

    private fun updateUserArticle(){
        article.title = titleEditText.text.toString()
        article.phoneNumber = phoneNumberEditText.text.toString()
        article.website = websiteEditText.text.toString()
        article.description = descEditText.text.toString()
        articleActivityViewModel.updateArticle(article)
    }

    private fun enabledEditText(enabled: Boolean){
        titleEditText.isEnabled = enabled
        websiteEditText.isEnabled = true
        descEditText.isEnabled = enabled
        phoneNumberEditText.isEnabled = true

    }

    private fun editableEditText(editable: Boolean){

        val backgroundEditText: Int
        if(editable){
            backgroundEditText = android.R.drawable.edit_text
            titleEditText.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        } else{
            backgroundEditText = android.R.color.transparent
            titleEditText.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
        }

        titleEditText.setBackgroundResource(backgroundEditText)
        titleEditText.isFocusable = !editable
        titleEditText.isFocusableInTouchMode = editable
        websiteEditText.setBackgroundResource(backgroundEditText)
        websiteEditText.isFocusable = !editable
        websiteEditText.isFocusableInTouchMode = editable
        descEditText.setBackgroundResource(backgroundEditText)
        descEditText.isFocusable = !editable
        descEditText.isFocusableInTouchMode = editable
        phoneNumberEditText.setBackgroundResource(backgroundEditText)
        phoneNumberEditText.isFocusable = !editable
        phoneNumberEditText.isFocusableInTouchMode = editable

        enabledEditText(editable)
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
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + "112")
            startActivity(intent)
            return true
        }

        if(id==R.id.action_sharing){
            val message = "${article.title}\n${article.phoneNumber}\n${article.website}\n\nOpis:\n${article.description}"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, article.title)
                putExtra(Intent.EXTRA_SUBJECT, message)
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            return true
        }

        return false
    }
}