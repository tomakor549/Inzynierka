package com.example.inzynierka.ui.shareData

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.*
import com.example.inzynierka.ui.profile.ProfileViewModel
import javax.inject.Inject

class ShareDataViewModel @Inject constructor(requireContext: Context) : ViewModel() {

    private var context: Context = requireContext
    private var MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
    private var WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

    @Suppress("UNCHECKED_CAST")
    class Factory constructor(
        private val con: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(con) as T
        }
    }

    //funkcja ma przyjmować jako argument tytuł nagłówka i wypełniony szablon nagłówka
    fun expandList(){
        var deleteImageButton = ImageButton(context)
        var editImageButton = ImageButton(context)
        var expandImageButton = ImageButton(context)
        var textHeaderView = TextView(context)

        //główny (zwracany) layaut zawierający wszystskie elementy
        var mainLayout = LinearLayout(context)
        //layout z danymi
        var headerRelativeLayout = RelativeLayout(context)
        //rozwijany layout
        var dataLinearLayout = LinearLayout(context)


    }

    private fun layoutParams(weight: Int, height: Int): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(weight, height)
    }
}
