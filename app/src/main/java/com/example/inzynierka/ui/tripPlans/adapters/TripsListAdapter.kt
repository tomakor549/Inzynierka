package com.example.inzynierka.ui.tripPlans.adapters

import android.app.Application
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.ui.tripPlans.adapters.selectTripPlan.TripPlanSelectActivity
import com.example.inzynierka.ui.tripPlans.room.Trip
import com.example.inzynierka.ui.tripPlans.room.TripRepository
import kotlinx.android.synthetic.main.trip_row.view.*

class TripsListAdapter(private val application: Application, private val listOfTrips: List<Trip>):RecyclerView.Adapter<TripViewHolder>(){

    private var tripRepository: TripRepository =
        TripRepository(application)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.trip_row, parent, false)
        return TripViewHolder(row)
    }

    override fun getItemCount(): Int {
        return listOfTrips.size
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val dataLabel = "Początek: ${listOfTrips[position].startDate}"
        val id = listOfTrips[position].tripId

        holder.tripId = id
        holder.dateTextView.text = dataLabel
        holder.titleTextView.text = listOfTrips[position].tripName


        holder.textLayout.setOnClickListener {
            val intent = Intent(application.applicationContext, TripPlanSelectActivity::class.java)
            intent.putExtra("tripId", listOfTrips[position].tripId)
            application.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            tripRepository.deleteTripById(id)
            tripRepository.deletePlansById(id)

        }

        holder.editButton.setOnClickListener {
            //Toast.makeText(application,"Edytuj ${id}", Toast.LENGTH_SHORT)
        }
    }

}

class TripViewHolder(view: View): RecyclerView.ViewHolder(view){

    var tripId: Long = 0
    val dateTextView: TextView = view.trip_row_date
    val titleTextView: TextView = view.trip_row_title
    val textLayout: LinearLayout = view.trip_row_text_layout
    var deleteButton: ImageButton = view.trip_row_delete_button
    var editButton: ImageButton = view.trip_row_edit_button

}

