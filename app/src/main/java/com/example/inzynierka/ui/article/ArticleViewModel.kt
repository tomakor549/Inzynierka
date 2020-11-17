package com.example.inzynierka.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "nazwa uzytkownika"
    }
    val text: LiveData<String> = _text
}
