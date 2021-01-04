package com.example.inzynierka.ui.articles.articlesList.article

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.inzynierka.R
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private var title = ""
    private var desc = ""
    private var url = ""
    private var phoneNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val extras = intent.extras
        if (extras != null) {
            try {
                title = intent.getStringExtra("title")!!
                url = intent.getStringExtra("website")!!
                phoneNumber = intent.getStringExtra("phoneNumber")!!
                desc = intent.getStringExtra("description")!!
            }
            catch (e: Exception){
                Log.i("ArticleActivity", "dane sa nullami")
                onBackPressed()
            }
        }

        val tmp: String

        //sprawdzenie odwrotności numeru telefonu i strony internetowej
        if(!setPhoneAndUrl(phoneNumber, url)){
            if (!setPhoneAndUrl(url, phoneNumber)){
                phoneNumber = "-"
                url = "-"
            }
            else{
                tmp = phoneNumber
                phoneNumber = url
                url = tmp
            }
        }

        addToolbar()

        activity_article_title.setText(title)
        activity_article_website.setText(url)
        activity_article_phone.setText(phoneNumber)
        activity_article_description.setText(desc)
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

    private fun setPhoneAndUrl(phone: String, website: String): Boolean{
        var correct = false

        //sprawdzanie czy to adres strony
        if(URLUtil.isValidUrl(website)){
            activity_article_phone.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            activity_article_website.setOnClickListener {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(website))
                startActivity(browserIntent)
            }
            correct = true
        }


        if(phone.length<3)
            return correct

        //sprawdzanie czy to numer telefonu
        if(PhoneNumberUtils.isGlobalPhoneNumber(phone)){
            activity_article_phone.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            activity_article_phone.setOnClickListener{
                val telephoneIntent = Intent(Intent.ACTION_DIAL)
                telephoneIntent.data = Uri.parse("tel:${phone}")
                startActivity(telephoneIntent)
            }
            correct = true
        }

        return correct

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
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, title)
                putExtra(Intent.EXTRA_SUBJECT, title)
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