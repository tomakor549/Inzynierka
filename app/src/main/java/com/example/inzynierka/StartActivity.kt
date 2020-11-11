package com.example.inzynierka

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var phoneNumber: EditText
    private lateinit var nameNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

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

        user_ICE1.setOnClickListener{
            var contactPickIntent = Intent(Intent.ACTION_PICK)
            contactPickIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(contactPickIntent, 111)
        }

        user_ICE2.setOnClickListener{
            var contactPickIntent = Intent(Intent.ACTION_PICK)
            contactPickIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(contactPickIntent, 112)
        }

        user_ICE3.setOnClickListener{
            var contactPickIntent = Intent(Intent.ACTION_PICK)
            contactPickIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(contactPickIntent, 113)
        }

        val startApp = findViewById<Button>(R.id.button_saveData)
        startApp.setOnClickListener{
            saveData()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            111->{
                if(resultCode == Activity.RESULT_OK){
                    var contacturi = data?.data ?: return
                    var cols = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    var rs = contentResolver.query(contacturi, cols, null, null, null)
                    if(rs?.moveToFirst()!!){
                        user_ICE1.setText(rs.getString(0))
                        user_ICE1_name.setText(rs.getString(1) + ": ")
                    }
                }
            }
            112 ->{
                if(resultCode == Activity.RESULT_OK){
                    var contacturi = data?.data ?: return
                    var cols = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    var rs = contentResolver.query(contacturi, cols, null, null, null)
                    if(rs?.moveToFirst()!!){
                        user_ICE2.setText(rs.getString(0))
                        user_ICE2_name.setText(rs.getString(1) + ": ")
                    }
                }
            }
            113 ->{
                if(resultCode == Activity.RESULT_OK){
                    var contacturi = data?.data ?: return
                    var cols = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    var rs = contentResolver.query(contacturi, cols, null, null, null)
                    if(rs?.moveToFirst()!!){
                        user_ICE3.setText(rs.getString(0))
                        user_ICE3_name.setText(rs.getString(1) + ": ")
                    }
                }
            }
        }
    }

    fun checkData(): Boolean{
        return user.checkExistence()
    }

    fun saveData(){
        user.setName(user_name.toString())
        user.setICE1(user_ICE1.toString())
    }
}