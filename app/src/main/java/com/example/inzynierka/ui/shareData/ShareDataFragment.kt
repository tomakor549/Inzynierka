package com.example.inzynierka.ui.shareData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import com.example.inzynierka.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_share_data.*
import kotlinx.android.synthetic.main.fragment_share_data.view.*

class ShareDataFragment : Fragment() {

    //private lateinit var shareDataViewModel: ShareDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_share_data, container, false)

        //val factory = ProfileViewModel.Factory(requireContext())
        //shareDataViewModel = ViewModelProvider(this, factory).get(ShareDataViewModel::class.java)


        var rowTable = addShareData("Ola", "10.12.2020")
        //var weight = ViewGroup.LayoutParams.WRAP_CONTENT
        //var height = ViewGroup.LayoutParams.WRAP_CONTENT
        //var layoutParams = ViewGroup.LayoutParams(weight, height)

        val table = root.share_data_table
        table.isStretchAllColumns = true
        /*val textView = TextView(requireContext())
        textView.text = "habdjkan ak f lkdsflkds"
        textView.layoutParams = layoutParams*/
        table.addView(rowTable)

        return root
    }

    private fun addShareData(name: String, dayEnd: String): TableRow {

        val userName = TextView(context, null, 0, R.style.MyStyles_TableUserNameTextView)
        userName.text = name

        val time = TextView(context, null, 0, R.style.MyStyles_TableUserTimeTextView)
        time.text=dayEnd


        //główny (zwracany) layaut zawierający wszystskie elementy
        val table: TableRow = TableRow(context)
        table.addView(userName)
        table.addView(time)
        return table
    }

    private fun layoutParams(weight: Int, height: Int): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(weight, height)
    }
}
