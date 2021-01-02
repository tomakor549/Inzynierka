package com.example.inzynierka.ui.profile


import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import com.example.inzynierka.StartActivity
import com.example.inzynierka.ui.articles.articlesList.ArticlesActivity
import com.example.inzynierka.ui.articles.enum.ArticleNameEnum
import kotlinx.android.synthetic.main.fragment_profile.*
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

    private fun editData(){
        //Ukrycie TextView
        profile_user_name.visibility = TextView.GONE
        profile_ice1.visibility = TextView.GONE
        profile_ice2.visibility = TextView.GONE
        profile_ice3.visibility = TextView.GONE
        profile_bloodType.visibility = TextView.GONE
        profile_illnesses.visibility = TextView.GONE
        profile_medicines.visibility = TextView.GONE

        //pokazanie pól edycyjnych
        profile_user_name_edit.visibility = EditText.VISIBLE
        profile_ice1_edit.visibility = EditText.VISIBLE
        profile_ice2_edit.visibility = EditText.VISIBLE
        profile_ice3_edit.visibility = EditText.VISIBLE
        profile_illnesses.visibility = EditText.VISIBLE
        profile_medicines.visibility = EditText.VISIBLE
        profile_blood_name.visibility = Spinner.VISIBLE
        profile_blood_Rh.visibility = Spinner.VISIBLE
    }

    private fun viewData(){
        //Ukrycie TextView
        profile_user_name.visibility = TextView.VISIBLE
        profile_ice1.visibility = TextView.VISIBLE
        profile_ice2.visibility = TextView.VISIBLE
        profile_ice3.visibility = TextView.VISIBLE
        profile_bloodType.visibility = TextView.VISIBLE
        profile_illnesses.visibility = TextView.VISIBLE
        profile_medicines.visibility = TextView.VISIBLE

        //pokazanie pól edycyjnych
        profile_user_name_edit.visibility = EditText.GONE
        profile_ice1_edit.visibility = EditText.GONE
        profile_ice2_edit.visibility = EditText.GONE
        profile_ice3_edit.visibility = EditText.GONE
        profile_illnesses.visibility = EditText.GONE
        profile_medicines.visibility = EditText.GONE
        profile_blood_name.visibility = Spinner.GONE
        profile_blood_Rh.visibility = Spinner.GONE
    }

}