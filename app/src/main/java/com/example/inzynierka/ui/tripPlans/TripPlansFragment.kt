package com.example.inzynierka.ui.tripPlans

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.room.trip.Plan
import com.example.inzynierka.room.trip.Trip
import com.example.inzynierka.ui.tripPlans.adapters.TripsListAdapter
import com.example.inzynierka.ui.tripPlans.addTrip.AddTripActivity
import kotlinx.android.synthetic.main.fragment_trip_plans.view.*

class TripPlansFragment : Fragment() {

    private lateinit var tripPlansViewModel: TripPlansViewModel
    private var tripsListAdapter: TripsListAdapter? = null
    private lateinit var tripPlansRecyclerView: RecyclerView
    private lateinit var listOfTrip: LiveData<List<Trip>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_trip_plans, container, false)
        //val factory = ProfileViewModel.Factory(requireContext())
        //tripPlansViewModel = ViewModelProvider(this, factory).get(TripPlansViewModel::class.java)
        tripPlansViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(TripPlansViewModel::class.java)

        clickFab(root)

        tripPlansRecyclerView = root.trip_plans_recyclerView

        tripPlansRecyclerView.layoutManager = LinearLayoutManager(context)

        listOfTrip = tripPlansViewModel.getAllTrip()
        listOfTrip.observe(requireActivity(), {
            if(it.isNotEmpty()){
                tripsListAdapter = TripsListAdapter(ArrayList(it))
                tripPlansRecyclerView.adapter = tripsListAdapter
            }
        })

        return root
    }

    private fun clickFab(root:View){
        root.trip_plans_add.setOnClickListener {
            val intent = Intent(activity, AddTripActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        val deleteTrips: List<Long>? = tripsListAdapter?.getTripDeleteList()
        if(deleteTrips != null){
            for(tripId in deleteTrips)
                tripPlansViewModel.deleteTripAndPlansById(tripId)
        }
    }
}
