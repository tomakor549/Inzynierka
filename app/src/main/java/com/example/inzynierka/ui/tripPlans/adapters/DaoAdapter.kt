package com.example.inzynierka.ui.tripPlans.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.ui.tripPlans.room.Trip
import kotlinx.android.synthetic.main.trip_row.view.*

class DaoAdapter(private val listOfTrips: List<Trip>):RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.trip_row, parent, false)
        return MyViewHolder(row)
    }

    override fun getItemCount(): Int {
        return listOfTrips.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.dateTextView.text = listOfTrips[position].data
        holder.titleTextView.text = listOfTrips[position].name
    }

}

class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
    val dateTextView: TextView = view.trip_row_data
    val titleTextView: TextView = view.trip_row_title
}

