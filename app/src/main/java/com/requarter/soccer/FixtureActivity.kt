package com.requarter.soccer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.requarter.soccer.data.api.ApiHelper
import com.requarter.soccer.data.api.ApiServiceImpl
import com.requarter.soccer.data.model.Team
import com.requarter.soccer.ui.base.ViewModelFactory
import com.requarter.soccer.ui.main.view.NextFixtureFragment
import com.requarter.soccer.ui.main.view.PreviousFixtureFragment
import com.requarter.soccer.ui.main.viewmodel.TeamViewModel
import com.requarter.soccer.utils.Status
import kotlinx.android.synthetic.main.activity_fixture.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progressBar

class FixtureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixture)
        val adapter = FixtureViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(PreviousFixtureFragment(),"Previous")
        adapter.addFragment(NextFixtureFragment(),"Next")
        viewPager.adapter = adapter
    }

    class FixtureViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList : MutableList<String> = ArrayList()
        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        fun addFragment(fragment: Fragment,title: String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}