package com.requarter.soccer.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.requarter.soccer.R
import com.requarter.soccer.data.api.ApiHelper
import com.requarter.soccer.data.api.ApiServiceImpl
import com.requarter.soccer.data.model.Team
import com.requarter.soccer.ui.base.ViewModelFactory
import com.requarter.soccer.ui.main.adapter.MainAdapter
import com.requarter.soccer.ui.main.viewmodel.TeamViewModel
import com.requarter.soccer.utils.LocalStorages
import com.requarter.soccer.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var teamViewModel: TeamViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkTheme()
        setupUI()
        setupViewModel()
        setupObserver()
        drawFixtureBtn.setOnClickListener {
            val intent = Intent(this, FixtureActivity::class.java)
            startActivity(intent)
        }
        btnChangeTheme.setOnClickListener {
            chooseTheme()
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
        teamViewModel.getTeams().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS-> {
                    progressBar.visibility = View.GONE
                    it.data?.let { teams -> renderList(teams) }
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

    private fun chooseTheme() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.theme_choose_text))
        val styles = arrayOf("Light","Dark")
        val checkedItem = LocalStorages(this).darkTheme
        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->
            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    delegate.applyDayNight()
                    LocalStorages(this).darkTheme = 0
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    delegate.applyDayNight()
                    LocalStorages(this).darkTheme = 1
                    dialog.dismiss()
                }
            }

        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun checkTheme() {
        when (LocalStorages(this).darkTheme) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }
}