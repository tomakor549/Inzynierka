package com.example.inzynierka.ui.article

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Pusty element"
    }
    val text: LiveData<String> = _text
}
