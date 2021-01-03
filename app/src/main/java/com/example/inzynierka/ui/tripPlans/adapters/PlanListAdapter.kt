package com.example.inzynierka.ui.tripPlans.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inzynierka.R
import com.example.inzynierka.room.Plan
import kotlinx.android.synthetic.main.show_plan_row.view.*

class PlanListAdapter(private var plansList: List<Plan>): RecyclerView.Adapter<PlansViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.show_plan_row, parent, false)
        return PlansViewHolder(row)
    }

    override fun getItemCount(): Int {
        return plansList.size
    }

    override fun onBindViewHolder(holder: PlansViewHolder, position: Int) {
        val str = "dzień ${position+1}"
        holder.dayTextView.text = str
        holder.descTextView.text = plansList[position].description
    }
}

class PlansViewHolder(view: View): RecyclerView.ViewHolder(view){
    val dayTextView: TextView = view.show_plan_row_day
    val descTextView: TextView = view.show_plan_row_desc
}

