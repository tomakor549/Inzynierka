package com.example.inzynierka.ui.tripPlans.adapters

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.ui.tripPlans.selectTripPlan.TripPlanSelectActivity
import com.example.inzynierka.ui.tripPlans.editTrip.TripEditActivity
import com.example.inzynierka.room.trip.Trip
import com.example.inzynierka.room.trip.TripRepository
import kotlinx.android.synthetic.main.trip_row.view.*

class TripsListAdapter(private val listOfTrips: ArrayList<Trip>):RecyclerView.Adapter<TripViewHolder>(){
    private var deleteTripsListId = ArrayList<Long>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.trip_row, parent, false)
        return TripViewHolder(row)
    }

    override fun getItemCount(): Int {
        return listOfTrips.size
    }

    private fun removeItem(position: Int) {
        //val id = listOfTrips[position].tripId
        deleteTripsListId.add(listOfTrips[position].tripId)
        listOfTrips.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getTripDeleteList(): List<Long>?{
        if (deleteTripsListId.isNotEmpty())
            return deleteTripsListId.toList()

        return null
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val dataLabel = "${listOfTrips[position].startDate} - ${listOfTrips[position].endDate}"
        val id = listOfTrips[position].tripId

        val builder = AlertDialog.Builder(holder.deleteButton.context)
        builder.setTitle("Usuwanie planu wycieczki")
        builder.setMessage("Na pewno chcesz usunąć \"${listOfTrips[position].tripName}\"?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("Tak") { _, _ ->
            try{
                removeItem(position)
            }
            catch (e: Exception){
                Log.i("TripsListAdapter", "Wyjątek w usuwaiu danych")
            }
        }

        builder.setNegativeButton("Nie") { _, _ ->

        }

        holder.tripId = id
        holder.dateTextView.text = dataLabel
        holder.titleTextView.text = listOfTrips[position].tripName


        holder.textLayout.setOnClickListener {
            val intent = Intent(holder.textLayout.context, TripPlanSelectActivity::class.java)
            intent.putExtra("tripId", listOfTrips[position].tripId)
            holder.textLayout.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            builder.show()
        }

        holder.editButton.setOnClickListener {
            val intent = Intent(holder.editButton.context, TripEditActivity::class.java)
            intent.putExtra("tripId", listOfTrips[position].tripId)
            holder.editButton.context.startActivity(intent)
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

