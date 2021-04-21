package com.requarter.soccer.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.requarter.soccer.R
import com.requarter.soccer.data.model.Team
import kotlinx.android.synthetic.main.item_layout.view.*
class MainAdapter(
    private val teams: ArrayList<Team>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(team: Team) {
            itemView.textViewUserName.text = team.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(teams[position])

    fun addData(list: List<Team>) {
        teams.addAll(list)
    }

}