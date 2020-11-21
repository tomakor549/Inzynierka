package com.example.inzynierka

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.fragment_profile.*

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

        user_ICE1.setOnClickListener{
            phoneNumber = user_ICE1
            nameNumber = user_ICE1_name

            var contactPickIntent = Intent(Intent.ACTION_PICK)
            contactPickIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(contactPickIntent, 111)
        }

        user_ICE2.setOnClickListener{
            phoneNumber = user_ICE2
            nameNumber = user_ICE2_name
            var contactPickIntent = Intent(Intent.ACTION_PICK)
            contactPickIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(contactPickIntent, 111)
        }

        user_ICE3.setOnClickListener{
            phoneNumber = user_ICE3
            nameNumber = user_ICE3_name
            var contactPickIntent = Intent(Intent.ACTION_PICK)
            contactPickIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(contactPickIntent, 111)
        }

        val startApp = button_saveData
        startApp.setOnClickListener{
            saveData()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            var contacturi = data?.data ?: return
            var cols = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            var rs = contentResolver.query(contacturi, cols, null, null, null)
            if(rs?.moveToFirst()!!){
                phoneNumber.setText(rs.getString(0))
                nameNumber.setText(rs.getString(1) + ": ")
            }
        }
        /* request
        when(requestCode){
            111->{
                if(resultCode == Activity.RESULT_OK){
                    var contacturi = data?.data ?: return
                    var cols = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    var rs = contentResolver.query(contacturi, cols, null, null, null)
                    if(rs?.moveToFirst()!!){
                        phoneNumber.setText(rs.getString(0))
                        nameNumber.setText(rs.getString(1) + ": ")

                        //user_ICE1.setText(rs.getString(0))
                        //user_ICE1_name.setText(rs.getString(1) + ": ")
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
        }*/
    }

    fun checkData(): Boolean{
        if(user_name.length() >= 5)
            if(user_ICE1.length()!=0 || user_ICE2.length()!=0 || user_ICE3.length()!=0)
                return true
        return false
    }

    private fun saveData(){
        user.setName("Tom Kor")
        /*if(profile_UserName.text.isNotEmpty()) {
            user.setName(user_name.text.toString())
        }

        if(!profile_ice1.text.isEmpty() || !profile_ice2.text.isEmpty() || !profile_ice3.text.isEmpty()){
            user.setICE1(user_ICE1.text.toString())
            user.setICE2(user_ICE2.text.toString())
            user.setICE3(user_ICE3.text.toString())
        }

        if(!profile_illnesses.text.isEmpty())
            user.setIllnesses(user_ill.text.toString())
        if(!profile_medicines.text.isEmpty())
            user.setMedicines(user_medicines.text.toString())*/
    }
}