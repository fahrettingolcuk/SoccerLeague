package com.requarter.soccer.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.requarter.soccer.R
import com.requarter.soccer.ui.main.adapter.FixtureViewPagerAdapter
import kotlinx.android.synthetic.main.activity_fixture.*

class FixtureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixture)
        val adapter = FixtureViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(PreviousFixtureFragment())
        adapter.addFragment(NextFixtureFragment())
        viewPager.adapter = adapter
    }

}