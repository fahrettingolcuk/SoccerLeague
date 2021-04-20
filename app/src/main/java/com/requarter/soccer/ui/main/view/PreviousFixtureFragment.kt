package com.requarter.soccer.ui.main.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.requarter.soccer.R
import com.requarter.soccer.data.api.ApiHelper
import com.requarter.soccer.data.api.ApiServiceImpl
import com.requarter.soccer.data.model.Team
import com.requarter.soccer.ui.base.ViewModelFactory
import com.requarter.soccer.ui.main.viewmodel.TeamViewModel
import com.requarter.soccer.utils.Status
import kotlinx.android.synthetic.main.fragment_previous_fixture.*

class PreviousFixtureFragment : Fragment() {
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
        previous_fragment_name.text = "Previous Fixture"
        teamViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(TeamViewModel::class.java)
        setupObserver()
    }

    private fun setupObserver() {
        teamViewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS-> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun renderList(teams: List<Team>) {
        Log.e("123teams",teams.toString())
        teamViewModel.createPreviousFixture()
    }
}