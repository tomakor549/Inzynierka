package com.example.inzynierka.ui.profile


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import com.example.inzynierka.StartActivity
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.runBlocking


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var phoneNumber: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val factory = ProfileViewModel.Factory(requireContext())
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        phoneNumber = ""

        /*
        try {
            profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        } catch (ex: ExceptionInInitializerError){
            Log.d("ProfileFragment", "nie można stworzyć profileViewModel")
        }*/

        //podkreslenie numerow telefonu
        root.profile_ice1.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        root.profile_ice2.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        root.profile_ice3.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        setData(root)

        setOnClickICENumber(root)

        root.profile_button.text = profileViewModel.buttonEditText

        root.profile_button.setOnClickListener{
            val intent = Intent(activity, StartActivity::class.java)
            intent.putExtra("edit", true)
            startActivity(intent)
        }

        root.profile_send_button.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, profileViewModel.sendUserData)
                putExtra(Intent.EXTRA_TITLE, profileViewModel.sendTitle)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        return root
    }

    private fun setData(root: View){
        profileViewModel.userName.observe(
            viewLifecycleOwner,
            Observer { root.profile_user_name.text = it })
        profileViewModel.userBloodType.observe(
            viewLifecycleOwner,
            Observer { root.profile_bloodType.text = it })
        profileViewModel.userICE1.observe(
            viewLifecycleOwner,
            Observer { root.profile_ice1.text = it })
        profileViewModel.userICE2.observe(
            viewLifecycleOwner,
            Observer { root.profile_ice2.text = it })
        profileViewModel.userICE3.observe(
            viewLifecycleOwner,
            Observer { root.profile_ice3.text = it })
        profileViewModel.userIllnesses.observe(
            viewLifecycleOwner,
            Observer { root.profile_illnesses.text = it })
        profileViewModel.userMedicines.observe(
            viewLifecycleOwner,
            Observer { root.profile_medicines.text = it })
    }

    private fun setOnClickICENumber(root: View) {
        if(!profileViewModel.userICE1.value.isNullOrEmpty())
            root.profile_ice1.setOnClickListener{
                callICE(profileViewModel.userICE1)

            }

        if(!profileViewModel.userICE2.value.isNullOrEmpty())
            root.profile_ice2.setOnClickListener{
                callICE(profileViewModel.userICE2)
            }

        if(!profileViewModel.userICE3.value.isNullOrEmpty()){
            root.profile_ice3.setOnClickListener{
                callICE(profileViewModel.userICE3)
            }
        }
    }

    private fun callICE(ice: LiveData<String>){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + ice.value.toString())
        startActivity(intent)
    }
}