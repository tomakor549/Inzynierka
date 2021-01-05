package com.example.inzynierka.ui.profile


import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import com.example.inzynierka.StartActivity
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val factory = ProfileViewModel.Factory(requireContext())
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)


        //podkreslenie numerow telefonu
        root.profile_ice1.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        root.profile_ice2.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        root.profile_ice3.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        setData(root)
        setClickAction(root)


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
        profileViewModel.editTextButton.observe(
            viewLifecycleOwner,
            Observer { root.profile_button.text = it }
        )
    }

    private fun setClickAction(root: View){
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

        setOnClickICENumber(root)

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