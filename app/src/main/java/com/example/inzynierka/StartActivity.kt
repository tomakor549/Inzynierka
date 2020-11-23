package com.example.inzynierka

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var phoneNumber: EditText
    private lateinit var nameNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(applicationContext!=null){
            user = User(applicationContext)
            Log.d("ProfileFragment", "stworzenie usera")
        }
        else
            Log.d("ProfileFragment", "nie ma context")

        if(user.checkExistence())
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setContentView(R.layout.activity_start)

        hideKeyboardEmptyField()
        onClickListeners()
        setButton()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideKeyboardEmptyField() {
        //ukrywanie klawiatury po kliknieciu na puste pole
        findViewById<View>(R.id.start_main_layout).setOnTouchListener { v, event ->
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            return@setOnTouchListener true
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

    private fun onClickListeners(){

        user_ICE1.setOnClickListener{
            phoneNumber = user_ICE1
            nameNumber = user_ICE1_name
            val contactPickIntent = Intent(Intent.ACTION_PICK)
            contactPickIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(contactPickIntent, 111)
        }

        user_ICE2.setOnClickListener{
            phoneNumber = user_ICE2
            nameNumber = user_ICE2_name
            val contactPickIntent = Intent(Intent.ACTION_PICK)
            contactPickIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(contactPickIntent, 111)
        }

        user_ICE3.setOnClickListener{
            phoneNumber = user_ICE3
            nameNumber = user_ICE3_name
            val contactPickIntent = Intent(Intent.ACTION_PICK)
            contactPickIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(contactPickIntent, 111)
        }
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
                nameNumber.text = rs.getString(1) + ": "
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