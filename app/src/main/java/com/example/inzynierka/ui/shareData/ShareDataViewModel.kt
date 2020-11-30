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
import com.example.inzynierka.R
import javax.inject.Inject


class ShareDataViewModel @Inject constructor(requireContext: Context) : ViewModel() {

    private var context: Context = requireContext
    private var MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
    private var WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

    //funkcja ma przyjmować jako argument tytuł nagłówka i wypełniony szablon nagłówka
    fun addShareData(name: String, dayEnd: String){

        var userName = TextView(context, null, 0, R.style.MyStyles_TableUserNameTextView)

        TextViewCompat.setTextAppearance(userName)
        userName.setTypeface(this)
        // ustawianie textview
        (userName.layoutParams as ConstraintLayout.LayoutParams).apply {
            // individually set text view any side margin
            setMargins(0, 0, 2, 2)

        }

        userName.layoutParams = layoutParams(0, WRAP_CONTENT)
        userName.gravity = Gravity.CENTER
        userName.setBackgroundColor(Color.WHITE)

        var time = TextView(context)
        //główny (zwracany) layaut zawierający wszystskie elementy
        val table = TableRow(context)
        var color: Color
        var f: Float = 2F


        time.layoutParams = layoutParams(0, WRAP_CONTENT)

            return table
    }

    private fun layoutParams(weight: Int, height: Int): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(weight, height)
    }
}
