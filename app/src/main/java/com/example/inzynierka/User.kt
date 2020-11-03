package com.example.inzynierka

import android.content.Context
import android.content.SharedPreferences

//shared preferences
class User (val context: Context) {//context przez user

    private val PREFERENCES_KEY = "profile"
    private val KEY_NAME = "userName"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)

    fun setName(data: String){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, data!!)

        editor.commit()
    }

    fun getName(): String {
        return sharedPref.getString(KEY_NAME, "-")!!
    }

    fun checkExistence(): Boolean{
        if(sharedPref.contains(KEY_NAME))
            return false

        return true
    }

    fun clear(){
        //nie czyści - nie wiem dlaczego
        sharedPref.edit().clear().commit();
    }
}