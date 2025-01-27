package com.example.inzynierka.ui.tripPlans.selectTripPlan

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inzynierka.R
import com.example.inzynierka.ui.tripPlans.adapters.PlanListAdapter
import com.example.inzynierka.room.trip.Plan
import com.example.inzynierka.room.trip.TripWithPlans
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
        val date = "${tripPlan.trip.startDate} – ${tripPlan.trip.endDate}"
        trip_select_date_text.text = date
        listPlans = tripPlan.plans

        val plansListAdapter = PlanListAdapter(listPlans)
        chosen_trip_plans_days_recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        chosen_trip_plans_days_recyclerView.adapter = plansListAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // dodanie menu
        menuInflater.inflate(R.menu.toolbar_sharing_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id==R.id.action_emergency){
            callEmergency()
            return true
        }
        if(id==R.id.action_sharing){
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, tripPlanSelectViewModel.convertTripToString(tripPlan))
                putExtra(Intent.EXTRA_SUBJECT, tripPlan.trip.tripName)
                putExtra(Intent.EXTRA_TITLE, tripPlan.trip.tripName)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
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