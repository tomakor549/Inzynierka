package com.example.inzynierka.ui.shareData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import com.example.inzynierka.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_share_data.*
import kotlinx.android.synthetic.main.fragment_share_data.view.*

class ShareDataFragment : Fragment() {

    private lateinit var shareDataViewModel: ShareDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val factory = ProfileViewModel.Factory(requireContext())
        shareDataViewModel = ViewModelProvider(this, factory).get(ShareDataViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_share_data, container, false)

        var weight = ViewGroup.LayoutParams.WRAP_CONTENT
        var height = ViewGroup.LayoutParams.WRAP_CONTENT
        var layoutParams = ViewGroup.LayoutParams(weight, height)

        val linearLayout = root.share_data_main_linearLayout
        val textView = TextView(requireContext())
        textView.text = "habdjkan ak f lkdsflkds"
        textView.layoutParams = layoutParams
        root.share_data_main_linearLayout.addView(textView)

        return root
    }
}
