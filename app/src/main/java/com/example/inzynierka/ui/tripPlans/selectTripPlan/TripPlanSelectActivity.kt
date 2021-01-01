package com.example.inzynierka.ui.tripPlans.adapters.selectTripPlan

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.ui.tripPlans.adapters.PlanListAdapter
import com.example.inzynierka.ui.tripPlans.adapters.TripsListAdapter
import com.example.inzynierka.ui.tripPlans.addTrip.AddTripViewModel
import com.example.inzynierka.ui.tripPlans.room.Plan
import com.example.inzynierka.ui.tripPlans.room.TripWithPlans
import com.example.inzynierka.ui.tripPlans.selectTripPlan.TripPlanSelectViewModel
import kotlinx.android.synthetic.main.activity_trip_add.*
import kotlinx.android.synthetic.main.activity_trip_select.*

class TripPlanSelectActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var tripPlanSelectViewModel: TripPlanSelectViewModel
    private lateinit var tripPlan: TripWithPlans
    private lateinit var listPlans: List<Plan>

    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_select)

        tripPlanSelectViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(TripPlanSelectViewModel::class.java)

        val extras = intent.extras
        if (extras != null) {
            id = extras.getLong("tripId")
            tripPlan = tripPlanSelectViewModel.getTripWithPlans(id!!)
        }
        else{
            Toast.makeText(this, "Nie znaleziono takiego planu", Toast.LENGTH_SHORT).show()
        }
        addToolbar(tripPlan.trip.tripName)
        val date = "Dzień ${tripPlan.trip.startDate}"
        trip_select_date_text.text = date
        listPlans = tripPlan.plans

        val plansListAdapter = PlanListAdapter(listPlans)
        chosen_trip_plans_days_recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        chosen_trip_plans_days_recyclerView.adapter = plansListAdapter

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
        toolbar = toolbar_select_trip as Toolbar
        toolbar.title = title
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }


}