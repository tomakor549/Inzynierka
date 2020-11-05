package com.example.inzynierka.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.inzynierka.R
import com.example.inzynierka.User

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(context!=null){
            user = User(requireContext())
            Log.d("ProfileFragment", "stworzenie usera")
        }
        else
            Log.d("ProfileFragment", "nie ma context")


        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val userName: TextView = root.findViewById(R.id.text_UserName)
        Log.d("ProfileFragment", "podtawowe ustawienie Profilu")

        //user.setName("Tomek K")
        //Log.d("ProfileFragment", "wczytanie danych usera")

        profileViewModel.getName(user.getName())

        Log.d("ProfileFragment", "wpisanie imienia")

        profileViewModel.userName.observe(viewLifecycleOwner, Observer { userName.text = it })
        Log.d("ProfileFragment", "ustaiwnei observera")

        return root
    }

}