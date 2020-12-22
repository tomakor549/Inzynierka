package com.example.inzynierka.ui.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "nazwa uzytkownika"
    }
    val text: LiveData<String> = _text
}
