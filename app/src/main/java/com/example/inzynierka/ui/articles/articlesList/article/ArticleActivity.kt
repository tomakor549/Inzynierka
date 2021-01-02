package com.example.inzynierka.ui.articles.articlesList.article

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.Menu
import android.view.MenuItem
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.inzynierka.R
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private var url = ""
    private var phoneNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val extras = intent.extras
        if (extras != null) {
            activity_article_title.text = intent.getStringExtra("title")
            activity_article_website.text = intent.getStringExtra("website")
            activity_article_phone.text = intent.getStringExtra("phoneNumber")
            activity_article_description.text = intent.getStringExtra("description")
        }

        url = activity_article_website.text.toString()
        //sprawdzanie czy to adres strony
        if(URLUtil.isValidUrl(url)){
            activity_article_phone.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            activity_article_website.setOnClickListener {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
        }

        phoneNumber = activity_article_phone.text.toString()
        //sprawdzanie czy to numer telefonu
        if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
            activity_article_phone.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            activity_article_phone.setOnClickListener{
                val telephoneIntent = Intent(Intent.ACTION_DIAL)
                telephoneIntent.data = Uri.parse("tel:" + activity_article_phone.text.toString())
                startActivity(telephoneIntent)
            }
        }

        addToolbar()
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
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id==R.id.action_emergency){
            callEmergency()
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