package com.example.inzynierka

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle

//shared preferences
class User {//context przez user

    private val PREFERENCES_KEY = "profile"
    private val KEY_USER_NAME = "userName"
    private val KEY_ICE_1 = "ICE1"
    private val KEY_ICE_2 = "ICE2"
    private val KEY_ICE_3 = "ICE3"
    private val KEY_MEDICINES = "medicines"
    private val KEY_ILLNESSES = "diseases"
    private lateinit var sharedPref: SharedPreferences

    constructor (context: Context) {
        sharedPref =  context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    fun setIllnesses(data: String){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_ILLNESSES, data)

        editor.apply()
    }

    fun getIllnesses(): String {
        return sharedPref.getString(KEY_ILLNESSES, "-")!!
    }

    fun setMedicines(data: String){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_MEDICINES, data)

        editor.apply()
    }

    fun getMedicines(): String {
        return sharedPref.getString(KEY_MEDICINES, "-")!!
    }

    fun setName(data: String){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_USER_NAME, data)

        editor.apply()
    }

    fun getName(): String {
        return sharedPref.getString(KEY_USER_NAME, "-")!!
    }

    fun setICE1(data: String){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_ICE_1, data)

        editor.apply()
    }

    fun getICE1(): String {
        return sharedPref.getString(KEY_ICE_1, "-")!!
    }

    fun setICE2(data: String){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_ICE_2, data)

        editor.apply()
    }

    fun getICE2(): String {
        return sharedPref.getString(KEY_ICE_2, "-")!!
    }

    fun setICE3(data: String){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_ICE_3, data)

        editor.apply()
    }

    fun getICE3(): String {
        return sharedPref.getString(KEY_ICE_3, "-")!!
    }

    fun checkExistence(): Boolean{
        if(sharedPref.contains(KEY_USER_NAME))
            return false

        return true
    }

    fun clear(){
        //nie czyści - nie wiem dlaczego
        sharedPref.edit().clear().apply();
    }
}