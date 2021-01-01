package com.example.inzynierka.ui.tripPlans.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.ui.tripPlans.addTrip.TripPlansList
import com.example.inzynierka.ui.tripPlans.room.Plan
import kotlinx.android.synthetic.main.plan_row.view.*

class TripPlansListAdapter(private var listOfPlans: List<Plan>):RecyclerView.Adapter<TripPlanViewHolder>(){

    var viewHolder = ArrayList<TripPlanViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripPlanViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.plan_row, parent, false)
        return TripPlanViewHolder(row)
    }

    override fun getItemCount(): Int {
        return listOfPlans.size
    }

    override fun onBindViewHolder(holder: TripPlanViewHolder, position: Int) {
        val str = "dzień ${position+1}"
        holder.dayTextView.text = str
        holder.dayPosition = position
        viewHolder.add(holder)
    }

    fun getPlanList(): List<Plan>{
        var str: String
        for(holder in viewHolder){
            str = holder.descEditTextView.text.toString()
            if(str.isNotBlank())
                listOfPlans[holder.dayPosition].description = str
            else
                listOfPlans[holder.dayPosition].description = "-"
        }
        return listOfPlans
    }
}

class TripPlanViewHolder(view: View): RecyclerView.ViewHolder(view){
    var dayPosition:Int = 0
    val dayTextView: TextView = view.plan_row_day
    val descEditTextView: EditText = view.plan_row_desc
}

