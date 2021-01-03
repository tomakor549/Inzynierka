package com.example.inzynierka

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*


class StartActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var phoneNumber: EditText

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

        if(!edit){
            if(user.checkExistence())
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        else{
            setFields()
        }


        setupUI(findViewById<View>(R.id.start_main_layout))
        onClickTouchListeners()
        setButton()
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

    @SuppressLint("ClickableViewAccessibility")
    private fun hideKeyboardEmptyField() {
        //ukrywanie klawiatury po kliknieciu na puste pole
        findViewById<View>(R.id.start_main_layout).setOnTouchListener { _, _ ->
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            return@setOnTouchListener true
        }
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupUI(view: View) {
        //Nasłuchiwanie kliknięcia dla widoków inne niż EditText
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideSoftKeyboard(this)
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
            if(checkData()){
                saveData()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Imie i nazwisko są wymagane\nPotrzebny jest przynajmniej jeden numer ICE", Toast.LENGTH_SHORT).show()
            }
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
        val contactPickIntent = Intent(Intent.ACTION_PICK)
        contactPickIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        startActivityForResult(contactPickIntent, 111)
    }

    @SuppressLint("Recycle", "SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            val contacturi = data?.data ?: return
            val cols = arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
            val rs = contentResolver.query(contacturi, cols, null, null, null)
            if(rs?.moveToFirst()!!){
                phoneNumber.setText(rs.getString(0))
            }
        }
    }

    private fun checkData(): Boolean {
        if (user_name.text.length >= 5) {
            if (user_ICE1.text.isNotEmpty() || user_ICE2.text.isNotEmpty() || user_ICE3.text.isNotEmpty())
                return true
        }
        return false
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

}