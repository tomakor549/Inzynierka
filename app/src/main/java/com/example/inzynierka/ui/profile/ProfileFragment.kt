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
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.activity_start.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel()::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        if(context!=null){
            user = User(requireContext())
            Log.d("ProfileFragment", "stworzenie usera")
        }
        else
            Log.d("ProfileFragment", "nie ma context")

        //root.user_name = user.getName()

        /*profileViewModel.userName.observe(viewLifecycleOwner, Observer { root.profile_UserName.text = it })
        profileViewModel.userBloodType.observe(viewLifecycleOwner, Observer { root.profile_bloodType.text = it })
        profileViewModel.userICE1.observe(viewLifecycleOwner, Observer { root.profile_ice1.text = it })
        profileViewModel.userICE2.observe(viewLifecycleOwner, Observer { root.profile_ice2.text = it })
        profileViewModel.userICE3.observe(viewLifecycleOwner, Observer { root.profile_ice3.text = it })
        profileViewModel.userIllnesses.observe(viewLifecycleOwner, Observer { root.profile_illnesses.text = it })
        profileViewModel.userMedicines.observe(viewLifecycleOwner, Observer { root.profile_medicines.text = it })*/

        return root
    }

}