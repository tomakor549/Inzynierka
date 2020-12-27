package com.example.inzynierka.ui.tripPlans.addTrip

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.ui.tripPlans.adapters.TripPlansListAdapter
import com.example.inzynierka.ui.tripPlans.room.Plan
import com.example.inzynierka.ui.tripPlans.room.Trip
import kotlinx.android.synthetic.main.activity_add_trip.*
import java.util.*
import kotlin.collections.ArrayList


class AddTripActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var addTripViewModel: AddTripViewModel
    private lateinit var stringDate: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var tripPlansAdapter: TripPlansListAdapter
    val TAG = "AddTripActivity"

    private var startDatePicker: DatePicker? = null
    private var endDatePicker: DatePicker? = null
    private val maxTripDay = 30


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_trip)
        addToolbar()

        addTripViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(AddTripViewModel::class.java)

        setButton()
        hideKeyboardEmptyField()
    }

    private fun setButton(){
        setStartData()
        setEndData()
        activity_add_trip_confirm_button.setOnClickListener {
            hideKeyboard()
            if(saveTrip())
                onBackPressed()
        }
    }

    private fun setStartData(){
        add_trip_date_start_button.setOnClickListener {
            hideKeyboard()
            add_trip_date_start_layout.visibility = View.VISIBLE
        }
        add_trip_date_start_confirm_button.setOnClickListener {
            startDatePicker = add_trip_date_start_picker
            this.stringDate = startDatePicker!!.dayOfMonth.toString() + "/" +
                    (startDatePicker!!.month + 1).toString() + "/" + startDatePicker!!.year.toString()
            add_trip_date_start_button.text = stringDate
            add_trip_date_start_layout.visibility = View.GONE

            hideKeyboard()

            if(endDatePicker!=null)
                showAddingPlans()
        }
    }

    private fun setEndData(){
        add_trip_date_end_button.setOnClickListener {
            hideKeyboard()
            add_trip_date_end_layout.visibility = View.VISIBLE
        }
        add_trip_date_end_confirm_button.setOnClickListener {
            endDatePicker = add_trip_date_end_picker
            this.stringDate = endDatePicker!!.dayOfMonth.toString() + "/" +
                    (endDatePicker!!.month + 1).toString() + "/" + endDatePicker!!.year.toString()
            add_trip_date_end_button.text = stringDate
            add_trip_date_end_layout.visibility = View.GONE

            hideKeyboard()

            if(startDatePicker!=null)
                showAddingPlans()
        }
    }

    private fun hideKeyboard(){
        try {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            Toast.makeText(this,"Hide Keyboard Error",Toast.LENGTH_SHORT)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideKeyboardEmptyField() {
        //ukrywanie klawiatury po kliknieciu na puste pole
        findViewById<View>(R.id.activity_add_trip).setOnTouchListener { _, _ ->
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            return@setOnTouchListener true
        }
    }

    private fun showAddingPlans(){

        val day = checkDate()
        if(day <= 0)
            return

        activity_add_trip_confirm_button.visibility = View.VISIBLE

        recyclerView = trip_plans_day_recyclerView
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        var dayPlansList = ArrayList<TripPlansList>()

        var i = 1
        while(i<=day){
            dayPlansList.add(TripPlansList(i))
            i++
        }

        tripPlansAdapter = TripPlansListAdapter(dayPlansList)
        recyclerView.adapter = tripPlansAdapter

    }

    @SuppressLint("ShowToast")
    private fun saveTrip() : Boolean{
        if(add_trip_name.text.isEmpty() || add_trip_name.text.length < 2){
            Toast.makeText(this,"Wpisz nazwę wycieczki zawierającą minimum 2 znaki",Toast.LENGTH_SHORT)
            Log.i(TAG, "Wpisz nazwę wycieczki zawierającą minimum 2 znaki")
            return false
        }
        val tripName = add_trip_name.text.toString()
        val tripStartDate = add_trip_date_start_button.text.toString()
        val tripId = createTripId()

        val trip = Trip(tripName, tripId,tripStartDate)


        val plansList: List<TripPlansList>
        try {
            plansList = tripPlansAdapter.getList()
        }catch (e: java.lang.Exception){
            Log.i(TAG, "Nie znaleziono listy Adaptera")
            return false
        }

        var editText: EditText
        var i = 0
        while(i<plansList.size){
            try {
                editText = recyclerView.findViewHolderForAdapterPosition(i)!!.itemView.findViewById(R.id.plan_row_desc)
                plansList[i].description = editText.text.toString()
            }
            catch (ex: java.lang.Exception){
                Log.i(TAG, "Nie znaleziono itemView")
                return false
            }
            i++
        }
        addTripViewModel.insertTrip(trip)
        for(element in plansList){
            addTripViewModel.insertPlan(Plan(tripId,element.day,element.description))
        }
        return true
    }

    private fun createTripId(): Long{
        val tripListId = addTripViewModel.getAllTripId().sorted()

        var newTripId: Long = 0
        for (element in tripListId){
            if(newTripId!=element){
                break
            }
            newTripId++;
        }
        if(newTripId>=tripListId.size-1)
            newTripId= tripListId.size.toLong()

        return newTripId
    }

    @SuppressLint("ShowToast")
    private fun checkDate(): Int{
        if (startDatePicker != null && endDatePicker != null) {
            val startYear = startDatePicker!!.year
            val startMonth = startDatePicker!!.month
            val startDay = startDatePicker!!.dayOfMonth
            val endYear = endDatePicker!!.year
            val endMonth = endDatePicker!!.month
            val endDay = endDatePicker!!.dayOfMonth

            val calendar = Calendar.getInstance()

            val startDate = Date(startYear, startMonth, startDay)
            val endDate = Date(endYear, endMonth, endDay)


            val difference: Long = endDate.time - startDate.time
            val differenceDates = difference / (24 * 60 * 60 * 1000)

            if(startYear == endYear && startMonth == endMonth && startDay == endDay)
                return 1

            if(differenceDates>0){
                if(differenceDates<maxTripDay){
                    return (differenceDates.toInt()+1)
                }
                else{
                    Toast.makeText(this,"Wycieczka nie może przekroczyć ${maxTripDay} dni",Toast.LENGTH_SHORT)
                    return 0
                }
            }
            else{
                Toast.makeText(this,"Wycieczka musi się zaczynać przed jej zakończeniem",Toast.LENGTH_SHORT)
            }
        }
        return 0
    }

    private fun addToolbar(){
        // Dodawanie toolbara
        toolbar = toolbar_add_trip as Toolbar
        toolbar.title = "Dodawanie Wycieczki"
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
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
}