package com.example.inzynierka.ui.profile


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.inzynierka.R
import com.example.inzynierka.User
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    //private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val factory = ProfileViewModel.Factory(requireContext())
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        /*
        try {
            profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        } catch (ex: ExceptionInInitializerError){
            Log.d("ProfileFragment", "nie można stworzyć profileViewModel")
        }*/

        profileViewModel.userName.observe(viewLifecycleOwner, Observer { root.profile_UserName.text = it })
        profileViewModel.userBloodType.observe(viewLifecycleOwner, Observer { root.profile_bloodType.text = it })
        profileViewModel.userICE1.observe(viewLifecycleOwner, Observer { root.profile_ice1.text = it })
        profileViewModel.userICE2.observe(viewLifecycleOwner, Observer { root.profile_ice2.text = it })
        profileViewModel.userICE3.observe(viewLifecycleOwner, Observer { root.profile_ice3.text = it })
        profileViewModel.userIllnesses.observe(viewLifecycleOwner, Observer { root.profile_illnesses.text = it })
        profileViewModel.userMedicines.observe(viewLifecycleOwner, Observer { root.profile_medicines.text = it })

        return root
    }

}