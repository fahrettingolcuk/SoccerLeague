package com.requarter.soccer.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.requarter.soccer.R
import com.requarter.soccer.data.model.Fixture
import kotlinx.android.synthetic.main.fixture_item_layout.view.*

class PreviousFixtureAdapter(
        private val fixtureList: ArrayList<Fixture>
): RecyclerView.Adapter<PreviousFixtureAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(fixture: Fixture){
            itemView.home.text = fixture.home
            itemView.away.text = fixture.away
            itemView.homeScore.text = fixture.homeScore.toString()
            itemView.awayScore.text = fixture.awayScore.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.fixture_item_layout,
                    parent,
                    false
            ))

    override fun onBindViewHolder(holder: DataViewHolder, position: Int)  =
            holder.bind(fixtureList[position])

    override fun getItemCount(): Int = fixtureList.size

    fun addData(list: List<Fixture>) {
        fixtureList.addAll(list)
    }
}