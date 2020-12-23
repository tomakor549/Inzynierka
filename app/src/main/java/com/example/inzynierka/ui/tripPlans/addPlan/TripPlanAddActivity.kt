package com.example.inzynierka.ui.tripPlans.addPlan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.inzynierka.R
import kotlinx.android.synthetic.main.trip_plan_add.*

class TripPlanAddActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_plan_add)

        addToolbar()
    }

    private fun addToolbar(){
        // Dodawanie toolbara
        toolbar = toolbar_add_trip as Toolbar
        toolbar.title = "Dodawanie Planu"
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}