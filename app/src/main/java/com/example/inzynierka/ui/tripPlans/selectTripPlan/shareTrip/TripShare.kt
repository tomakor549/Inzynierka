package com.example.inzynierka.ui.tripPlans.selectTripPlan.shareTrip

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R

class TripShare : AppCompatActivity() {
    private lateinit var tripShareViewModel: TripShareViewModel
    var tripId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_share)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            onBackPressed()
        }
        val extras = intent.extras
        if (extras == null) {
            onBackPressed()
        }
        tripId = intent.getLongExtra("tripId", -1)

        if(tripId<0){
            onBackPressed()
        }

        tripShareViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(TripShareViewModel::class.java)
    }
}