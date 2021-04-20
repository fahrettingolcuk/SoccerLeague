package com.requarter.soccer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.requarter.soccer.data.api.ApiHelper
import com.requarter.soccer.data.api.ApiServiceImpl
import com.requarter.soccer.data.model.Team
import com.requarter.soccer.ui.base.ViewModelFactory
import com.requarter.soccer.ui.main.adapter.MainAdapter
import com.requarter.soccer.ui.main.viewmodel.TeamViewModel
import com.requarter.soccer.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var teamViewModel: TeamViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
        drawFixtureBtn.setOnClickListener {
            val intent = Intent(this,FixtureActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        teamViewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS-> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(teams: List<Team>) {
        adapter.addData(teams)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        teamViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(TeamViewModel::class.java)
    }
}