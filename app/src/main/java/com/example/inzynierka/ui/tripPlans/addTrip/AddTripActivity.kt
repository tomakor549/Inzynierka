package com.example.inzynierka.ui.tripPlans.addTrip

import android.app.Activity
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.ui.tripPlans.adapters.TripPlansListAdapter
import com.example.inzynierka.room.trip.Plan
import com.example.inzynierka.room.trip.Trip
import kotlinx.android.synthetic.main.activity_trip_add.*
import kotlinx.android.synthetic.main.activity_trip_add.activity_add_trip_confirm_button
import kotlinx.android.synthetic.main.activity_trip_add.add_trip_name
import kotlinx.android.synthetic.main.activity_trip_edit.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class AddTripActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var addTripViewModel: AddTripViewModel
    //private lateinit var stringDate: String
    private var recyclerView: RecyclerView? = null
    private lateinit var tripPlansAdapter: TripPlansListAdapter

    private val TAG = "AddTripActivity"
    private var tripId by Delegates.notNull<Long>()

    private var startDatePicker: DatePicker? = null
    private var endDatePicker: DatePicker? = null
    private val maxTripDay = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_add)
        addToolbar()

        addTripViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(AddTripViewModel::class.java)

        tripId = createTripId()

        if (startDatePicker != null) {
            startDatePicker = edit_trip_date_start_picker
        }

        setButton()
    }

    private fun setButton(){
        setStartData()
        setEndData()

        activity_add_trip_confirm_button.setOnClickListener {

            if(saveTrip()){
                onBackPressed()
            }

        }
    }

    private fun setStartData(){
        add_trip_date_start_button.setOnClickListener {
            hideSoftKeyboard()
            if(add_trip_date_end_layout.visibility == View.VISIBLE)
                add_trip_date_end_layout.visibility = View.GONE
            add_trip_date_start_layout.visibility = View.VISIBLE
        }
        add_trip_date_start_confirm_button.setOnClickListener {
            startDatePicker = add_trip_date_start_picker

            add_trip_date_start_button.text = getStringDate(startDatePicker!!)
            add_trip_date_start_layout.visibility = View.GONE

            hideSoftKeyboard()

            if(endDatePicker!=null)
                showAddingPlans()
        }
    }

    private fun setEndData(){
        add_trip_date_end_button.setOnClickListener {
            hideSoftKeyboard()
            if(add_trip_date_start_layout.visibility == View.VISIBLE)
                add_trip_date_start_layout.visibility = View.GONE
            add_trip_date_end_layout.visibility = View.VISIBLE
        }

        add_trip_date_end_confirm_button.setOnClickListener {
            endDatePicker = add_trip_date_end_picker

            add_trip_date_end_button.text = getStringDate(endDatePicker!!)
            add_trip_date_end_layout.visibility = View.GONE

            hideSoftKeyboard()

            if(startDatePicker!=null)
                showAddingPlans()
        }
    }

    private fun getStringDate(datePicker: DatePicker): String{
        //dni zaczynają się od 1
        val day = if(datePicker.dayOfMonth<10)
            "0" + datePicker.dayOfMonth.toString()
        else
            datePicker.dayOfMonth.toString()

        //miesiące zaczynają się od 0
        val month = if(datePicker.month<9)
            "0" + (datePicker.month + 1).toString()
        else
            (datePicker.month + 1).toString()

        return day + "/" + month + "/" + datePicker.year.toString()
    }

    private fun hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun showAddingPlans(){

        val day = checkDate()
        if(day <= 0)
            return

        if(trip_plans_add_layout.visibility != View.VISIBLE)
            trip_plans_add_layout.visibility = View.VISIBLE

        if(recyclerView == null){
            recyclerView = trip_plans_day_recyclerView
            recyclerView!!.layoutManager = LinearLayoutManager(applicationContext)
            val dayPlansList = ArrayList<Plan>()

            var i = 1
            while(i<=day){
                dayPlansList.add(
                    Plan(
                        tripId,
                        day,
                        ""
                    )
                )
                i++
            }

            tripPlansAdapter = TripPlansListAdapter(dayPlansList)
            recyclerView!!.adapter = tripPlansAdapter
        }
        else{
            val oldPlansList = ArrayList(tripPlansAdapter.getPlanList())
            val oldPlanListSize = oldPlansList.size
            val newPlansList = ArrayList<Plan>()
            var i = 1
            if(day==oldPlanListSize)
                setExistAdapter(oldPlansList)
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
                    setExistAdapter(newPlansList)
                }
                if(day<oldPlanListSize){
                    for(plan in oldPlansList){
                        if(i<=day){
                            newPlansList.add(plan)
                            i++
                        }
                    }
                    setExistAdapter(newPlansList)
                }
            }
        }
    }

    private fun setExistAdapter(arrayList: ArrayList<Plan>){
        tripPlansAdapter.setPlansList(arrayList)
        recyclerView!!.adapter = tripPlansAdapter
    }

    private fun saveTrip() : Boolean{
        if(add_trip_name.text.isEmpty() || add_trip_name.text.length < 2){
            Toast.makeText(this,"Wpisz nazwę wycieczki zawierającą minimum 2 znaki",Toast.LENGTH_SHORT).show()
            Log.i(TAG, "Wpisz nazwę wycieczki zawierającą minimum 2 znaki")
            return false
        }
        val tripName = add_trip_name.text.toString()
        val tripStartDate = add_trip_date_start_button.text.toString()
        val tripEndDate = add_trip_date_end_button.text.toString()

        val trip = Trip(
            tripName,
            tripId,
            tripStartDate,
            tripEndDate
        )

        val plansList: List<Plan>
        try {
            plansList = tripPlansAdapter.getPlanList()
        }catch (e: java.lang.Exception){
            Log.i(TAG, "Nie znaleziono listy Adaptera")
            Toast.makeText(this,"Nie znaleziono listy Adaptera", Toast.LENGTH_SHORT).show()
            return false
        }

        try{
            addTripViewModel.insertTrip(trip)
            addTripViewModel.insertPlans(plansList)

        }
        catch (e: java.lang.Exception){
            Log.i(TAG, "addError")
        }
        return true
    }

    private fun createTripId(): Long{
        var newTripId: Long = 0

        if(addTripViewModel.getAllTripId().isEmpty()){
            return newTripId
        }
        val tripListId = addTripViewModel.getAllTripId().sorted()

        for (element in tripListId){
            if(newTripId!=element){
                return newTripId
            }
            newTripId++
        }
        if(newTripId>=tripListId.size-1)
            newTripId= tripListId.size.toLong()

        return newTripId
    }

    @Suppress("DEPRECATION")
    private fun checkDate(): Int{
        if (startDatePicker != null && endDatePicker != null) {
            val startYear = startDatePicker!!.year
            val startMonth = startDatePicker!!.month
            val startDay = startDatePicker!!.dayOfMonth
            val endYear = endDatePicker!!.year
            val endMonth = endDatePicker!!.month
            val endDay = endDatePicker!!.dayOfMonth

            val startDate = Date(startYear, startMonth, startDay)
            val endDate = Date(endYear, endMonth, endDay)



            if(startYear == endYear && startMonth == endMonth && startDay == endDay)
                return 1

            val difference: Long = endDate.time - startDate.time
            val differenceDates = difference / (24 * 60 * 60 * 1000)

            if(differenceDates>0){
                if(differenceDates<maxTripDay){
                    if(trip_plans_add_layout.visibility != View.VISIBLE)
                        trip_plans_add_layout.visibility = View.VISIBLE
                    return (differenceDates.toInt()+1)
                }
                else{
                    Toast.makeText(this,"Wycieczka nie może przekroczyć ${maxTripDay} dni",Toast.LENGTH_SHORT).show()
                    return 0
                }
            }
            else{
                Toast.makeText(this,"Wycieczka musi się zaczynać przed jej zakończeniem",Toast.LENGTH_SHORT).show()
                if(trip_plans_add_layout.visibility != View.GONE)
                    trip_plans_add_layout.visibility = View.GONE
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