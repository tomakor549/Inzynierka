package com.example.inzynierka.ui.tripPlans.adapters

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.ui.tripPlans.adapters.selectTripPlan.TripPlanSelectActivity
import com.example.inzynierka.ui.tripPlans.editTrip.TripEditActivity
import com.example.inzynierka.ui.tripPlans.room.Trip
import com.example.inzynierka.ui.tripPlans.room.TripRepository
import kotlinx.android.synthetic.main.trip_row.view.*

class TripsListAdapter(private val application: Application, private val activityContext: Context, private val listOfTrips: ArrayList<Trip>):RecyclerView.Adapter<TripViewHolder>(){

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

    fun removeItem(position: Int) {
        val id = listOfTrips[position].tripId
        listOfTrips.removeAt(position)
        notifyItemRemoved(position)
        tripRepository.deleteTripById(id)
        tripRepository.deletePlansById(id)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val dataLabel = "${listOfTrips[position].startDate} - ${listOfTrips[position].endDate}"
        val id = listOfTrips[position].tripId

        val builder = AlertDialog.Builder(activityContext)
        builder.setTitle("Usuwanie planu wycieczki")
        builder.setMessage("Na pewno chcesz usunąć \"${listOfTrips[position].tripName}\"?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            removeItem(position)
        }

        builder.setNegativeButton(android.R.string.no) { _, _ ->

        }

        holder.tripId = id
        holder.dateTextView.text = dataLabel
        holder.titleTextView.text = listOfTrips[position].tripName


        holder.textLayout.setOnClickListener {
            val intent = Intent(activityContext, TripPlanSelectActivity::class.java)
            intent.putExtra("tripId", listOfTrips[position].tripId)
            application.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            builder.show()
        }

        holder.editButton.setOnClickListener {
            val intent = Intent(activityContext, TripEditActivity::class.java)
            intent.putExtra("tripId", listOfTrips[position].tripId)
            application.startActivity(intent)
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

