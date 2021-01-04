package com.example.inzynierka.ui.tripPlans.editTrip

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.ui.tripPlans.adapters.TripPlansListAdapter
import com.example.inzynierka.room.trip.Plan
import com.example.inzynierka.room.trip.Trip
import com.example.inzynierka.room.trip.TripWithPlans
import com.example.inzynierka.ui.tripPlans.TripDataControl
import kotlinx.android.synthetic.main.activity_trip_add.activity_add_trip_confirm_button
import kotlinx.android.synthetic.main.activity_trip_add.add_trip_name
import kotlinx.android.synthetic.main.activity_trip_edit.*
import kotlin.collections.ArrayList

class TripEditActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var editTripViewModel: TripEditViewModel
    private lateinit var tripPlan: TripWithPlans
    private lateinit var tripPlansAdapter: TripPlansListAdapter
    private lateinit var recyclerView: RecyclerView


    private val tripDateControl = TripDataControl()
    private var startDatePicker: DatePicker? = null
    private var endDatePicker: DatePicker? = null
    private var tripId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_edit)

        editTripViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(TripEditViewModel::class.java)

        val extras = intent.extras
        if (extras != null) {
            tripId = extras.getLong("tripId")
        }
        else{
            Toast.makeText(this, "Nie znaleziono takiego planu", Toast.LENGTH_SHORT).show()
        }
        val badId:Long = -1
        if(tripId != badId){
            tripPlan = editTripViewModel.getTripWithPlans(tripId)
        }else
            return

        addToolbar("Edycja Planu")

        hideKeyboardEmptyField()
        val date = "${tripPlan.trip.startDate} - ${tripPlan.trip.endDate}"
        activity_edit_date.text = date
        
        tripPlansAdapter = TripPlansListAdapter(ArrayList(tripPlan.plans))
        recyclerView = edit_trip_plans_day_recyclerView
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = tripPlansAdapter

        add_trip_name.setText(tripPlan.trip.tripName)

        activity_add_trip_confirm_button.setOnClickListener {
            hideKeyboard()
            if(updateTrip()){
                onBackPressed()
            }
        }

        startDatePicker = edit_trip_date_start_picker
        endDatePicker = edit_trip_date_end_picker

        edit_trip_date_start_button.text = tripPlan.trip.startDate
        edit_trip_date_end_button.text = tripPlan.trip.endDate
        startDatePicker!!.init(tripDateControl.getYear(tripPlan.trip.startDate)!!,
            tripDateControl.getMonth(tripPlan.trip.startDate)!!,
            tripDateControl.getDay(tripPlan.trip.startDate)!!,
            null)
        endDatePicker!!.init(tripDateControl.getYear(tripPlan.trip.endDate)!!,
            tripDateControl.getMonth(tripPlan.trip.endDate)!!,
            tripDateControl.getDay(tripPlan.trip.endDate)!!,
            null)

        setStartData()
        setEndData()
    }

    private fun updateDate(){
        val day: Int
        val differenceDate = tripDateControl.getCheckDate(startDatePicker, endDatePicker)

        if(differenceDate>=0){
            if(differenceDate<tripDateControl.maxTripDay){
                day = differenceDate + 1
            }
            else{
                Toast.makeText(this,"Wycieczka nie może przekroczyć ${tripDateControl.maxTripDay} dni",Toast.LENGTH_SHORT).show()
                return
            }
        }
        else{
            Toast.makeText(this,"Wycieczka musi się zaczynać przed jej zakończeniem",Toast.LENGTH_SHORT).show()
            return
        }
        val oldPlansList = ArrayList(tripPlansAdapter.getPlanList())
        val oldPlanListSize = oldPlansList.size
        val newPlansList = ArrayList<Plan>()
        var i = 1
        if(day==oldPlanListSize)
            setAdapter(oldPlansList)
        else{
            if(day>oldPlanListSize){
                for(plan in oldPlansList){
                    newPlansList.add(plan)
                    i++
                }
                while(i<=day){
                    newPlansList.add(
                        Plan(
                            tripId,
                            i,
                            ""
                        )
                    )
                    i++
                }
                setAdapter(newPlansList)
            }
            if(day<oldPlanListSize){
                for(plan in oldPlansList){
                    if(i<=day){
                        newPlansList.add(plan)
                        i++
                    }
                }
                setAdapter(newPlansList)
            }
        }

    }

    private fun setAdapter(arrayList: ArrayList<Plan>){
        tripPlansAdapter.setPlansList(arrayList)
        //recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = tripPlansAdapter
    }

    private fun setStartData(){
        edit_trip_date_start_button.setOnClickListener {
            hideKeyboard()
            if(edit_trip_date_end_layout.visibility == View.VISIBLE)
                edit_trip_date_end_layout.visibility = View.GONE
            edit_trip_date_start_layout.visibility = View.VISIBLE
        }
        edit_trip_date_start_confirm_button.setOnClickListener {
            startDatePicker = edit_trip_date_start_picker

            edit_trip_date_start_button.text = tripDateControl.getStringDate(startDatePicker!!)
            edit_trip_date_start_layout.visibility = View.GONE

            hideKeyboard()

            if(endDatePicker!=null)
                updateDate()
        }
    }

    private fun setEndData(){
        edit_trip_date_end_button.setOnClickListener {
            hideKeyboard()
            if(edit_trip_date_start_layout.visibility == View.VISIBLE)
                edit_trip_date_start_layout.visibility = View.GONE
            edit_trip_date_end_layout.visibility = View.VISIBLE
        }

        edit_trip_date_end_confirm_button.setOnClickListener {
            endDatePicker = edit_trip_date_end_picker

            edit_trip_date_end_button.text = tripDateControl.getStringDate(endDatePicker!!)
            edit_trip_date_end_layout.visibility = View.GONE

            hideKeyboard()

            if(startDatePicker!=null)
                updateDate()
        }
    }

    private fun updateTrip() : Boolean{
        if(add_trip_name.text.length > 60 || add_trip_name.text.length < 2){
            Toast.makeText(this,"Wpisz nazwę wycieczki zawierającą minimum 2 znaki i maksimum 60",Toast.LENGTH_SHORT).show()
            return false
        }
        val tripName = add_trip_name.text.toString()

        val trip = Trip(
            tripName,
            tripId,
            tripPlan.trip.startDate,
            tripPlan.trip.endDate
        )

        val plansList: List<Plan>
        try {
            plansList = tripPlansAdapter.getPlanList()
        }catch (e: java.lang.Exception){
            Toast.makeText(this,"Nie znaleziono listy Adaptera", Toast.LENGTH_SHORT).show()
            return false
        }

        editTripViewModel.updateTrip(trip)
        editTripViewModel.updatePlans(plansList)
        return true
    }


    private fun hideKeyboard(){
        try {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            Toast.makeText(this,"Hide Keyboard Error",Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideKeyboardEmptyField() {
        //ukrywanie klawiatury po kliknieciu na puste pole
        findViewById<View>(R.id.activity_edit_trip).setOnTouchListener { _, _ ->
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            return@setOnTouchListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // dodanie menu
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id==R.id.action_emergency){
            callEmergency()
            return true
        }

        return false
    }

    private fun callEmergency(){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + "112")
        startActivity(intent)
    }

    private fun addToolbar(title: String){
        // Dodawanie toolbara
        toolbar = toolbar_edit_trip as Toolbar
        toolbar.title = title
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}