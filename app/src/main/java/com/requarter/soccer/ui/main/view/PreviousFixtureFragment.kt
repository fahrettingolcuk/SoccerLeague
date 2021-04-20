package com.requarter.soccer.ui.main.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.requarter.soccer.R
import com.requarter.soccer.data.api.ApiHelper
import com.requarter.soccer.data.api.ApiServiceImpl
import com.requarter.soccer.data.model.Fixture
import com.requarter.soccer.ui.base.ViewModelFactory
import com.requarter.soccer.ui.main.adapter.MainAdapter
import com.requarter.soccer.ui.main.adapter.PreviousFixtureAdapter
import com.requarter.soccer.ui.main.viewmodel.TeamViewModel
import com.requarter.soccer.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_previous_fixture.*

class PreviousFixtureFragment : Fragment() {
    private lateinit var adapter: PreviousFixtureAdapter
    private lateinit var teamViewModel: TeamViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_fixture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(TeamViewModel::class.java)
        setupUI(view)
        setupObserver()
    }

    private fun setupUI(view: View) {
        recyclerViewPrevious.layoutManager = LinearLayoutManager(view.context)
        adapter = PreviousFixtureAdapter(arrayListOf())
        recyclerViewPrevious.addItemDecoration(
                DividerItemDecoration(
                        recyclerViewPrevious.context,
                        (recyclerViewPrevious.layoutManager as LinearLayoutManager).orientation
                )
        )
        recyclerViewPrevious.adapter = adapter
    }

    private fun setupObserver() {
        teamViewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS-> {
                    it.data?.let {  renderList() }
                    recyclerViewPrevious.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun renderList() {
       val fixtureList: ArrayList<Fixture> =  teamViewModel.createPreviousFixture()
        adapter.addData(fixtureList)
        adapter.notifyDataSetChanged()
    }
}