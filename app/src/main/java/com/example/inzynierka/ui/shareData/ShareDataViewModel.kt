package com.example.inzynierka.ui.shareData

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import com.example.inzynierka.ui.profile.ProfileViewModel
import javax.inject.Inject


class ShareDataViewModel @Inject constructor(requireContext: Context) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory constructor(
        private val con: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(con) as T
        }
    }

    private var context: Context = requireContext
    private var MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
    private var WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

    //funkcja ma przyjmować jako argument tytuł nagłówka i wypełniony szablon nagłówka
    fun addShareData(name: String, dayEnd: String):TableRow{

        val userName = TextView(context, null, 0, R.style.MyStyles_TableUserNameTextView)
        userName.text = name

        val time = TextView(context, null, 0, R.style.MyStyles_TableUserTimeTextView)
        time.text=dayEnd
        //główny (zwracany) layaut zawierający wszystskie elementy
        val table = TableRow(context)
        table.addView(userName)
        table.addView(time)
        return table
    }

    private fun layoutParams(weight: Int, height: Int): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(weight, height)
    }
}
