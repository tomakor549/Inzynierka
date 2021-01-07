package com.example.inzynierka

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.activity_start.view.*
import kotlinx.android.synthetic.main.activity_trip_select.*


class StartActivity : AppCompatActivity() {
    private lateinit var startActivityViewModel: StartActivityViewModel
    private lateinit var user: User
    private lateinit var phoneNumber: EditText
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val edit: Boolean
        val extras = intent.extras
        if (extras != null) {
            edit = intent.getBooleanExtra("edit", false)
        }
        else
            edit = false


        if(applicationContext!=null){
            user = User(applicationContext)
            Log.d("ProfileFragment", "stworzenie usera")
        }
        else
            Log.d("ProfileFragment", "nie ma context")

        setContentView(R.layout.activity_start)

        addToolbar(getString(R.string.app_name))

        startActivityViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(StartActivityViewModel::class.java)

        if(!edit){
            if(user.checkExistence())
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return
            }
            if (startActivityViewModel.getArticleNumber() == 0.toLong()) {
                fillDatabase()
            }
        }
        else{
            setFields()
        }

        setupUI(findViewById<View>(R.id.start_main_layout))
        onClickTouchListeners()
        setButton()
    }

    private fun fillDatabase(){
        val articlesList = ReadArticleFromFile(resources).articlesList.toList()
        startActivityViewModel.insertArticle(articlesList)
    }

    private fun setFields(){
        val bloodNameArray = resources.getStringArray(R.array.bloodName)
        val bloodTypeArray = resources.getStringArray(R.array.bloodRh)
        val bloodName = user.getBloodName()
        val bloodRh = user.getBloodRh()
        var i = 0
        for (bloodNameArrayElement in bloodNameArray){
            if(bloodName == bloodNameArrayElement)
                user_blood_name.setSelection(i)
            i++
        }
        i = 0
        for (bloodNameArrayElement in bloodTypeArray){
            if(bloodRh == bloodNameArrayElement)
                user_blood_Rh.setSelection(i)
            i++
        }

        user_name.setText(user.getName())
        user_ICE1.setText(user.getICE1())
        user_ICE2.setText(user.getICE2())
        user_ICE3.setText(user.getICE3())
        user_ill.setText(user.getIllnesses())
        user_medicines.setText(user.getMedicines())
    }

    private fun hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupUI(view: View) {
        //Nasłuchiwanie kliknięcia dla widoków inne niż EditText
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideSoftKeyboard()
                false
            }
        }

        //Jeśli jest to kontener układu, wykonaj iterację po elementach potomnych i rekurencji inicjatora.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    private fun setButton(){
        val startApp = go_to_app
        startApp.setOnClickListener{
            saveData()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        button_clear.setOnClickListener {
            user_ICE1.text.clear()
            user_ICE2.text.clear()
            user_ICE3.text.clear()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onClickTouchListeners(){

        user_ICE1.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                phoneNumber = user_ICE1
                setPhoneNumber()
            }
            false
        }

        user_ICE2.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                phoneNumber = user_ICE2
                setPhoneNumber()
            }
            false
        }

        user_ICE3.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                phoneNumber = user_ICE3
                setPhoneNumber()
            }
            false
        }
    }

    private fun setPhoneNumber(){
        //aktywność wyboru
        val contactPickIntent = Intent(Intent.ACTION_PICK)
        //ustawienie jej typu na numery telefonu
        contactPickIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        startActivityForResult(contactPickIntent, 111)
    }

    @SuppressLint("Recycle", "SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            val contacturi = data?.data ?: return
            val contact = arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )
            val cursor = contentResolver.query(contacturi, contact, null, null, null)
            if(cursor?.moveToFirst()!!){
                phoneNumber.setText(cursor.getString(0))
            }
        }
    }

    private fun saveData() {
        user.setName(user_name.text.toString())
        user.setICE1(user_ICE1.text.toString())
        user.setICE2(user_ICE2.text.toString())
        user.setICE3(user_ICE3.text.toString())
        user.setBloodType(
            user_blood_name.selectedItem.toString(),
            user_blood_Rh.selectedItem.toString()
        )
        user.setIllnesses(user_ill.text.toString())
        user.setMedicines(user_medicines.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // dodanie menu
        menuInflater.inflate(R.menu.toolbar_menu, menu)

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

        return false
    }

    private fun addToolbar(title: String) {
        // Dodawanie toolbara
        toolbar = start_toolbar as Toolbar
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

}