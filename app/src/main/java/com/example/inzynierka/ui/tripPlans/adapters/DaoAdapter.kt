package com.example.inzynierka.ui.tripPlans.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.ui.tripPlans.room.Trip
import kotlinx.android.synthetic.main.trip_row.view.*

class DaoAdapter(private val listOfTrips: List<Trip>):RecyclerView.Adapter<TripViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.trip_row, parent, false)
        return TripViewHolder(row)
    }

    override fun getItemCount(): Int {
        return listOfTrips.size
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.dateTextView.text = listOfTrips[position].startDate
        holder.titleTextView.text = listOfTrips[position].tripName
    }

}

class TripViewHolder(view: View): RecyclerView.ViewHolder(view){
    val dateTextView: TextView = view.trip_row_date
    val titleTextView: TextView = view.trip_row_title
}

