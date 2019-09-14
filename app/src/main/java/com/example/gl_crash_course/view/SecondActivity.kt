package com.example.gl_crash_course.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.gl_crash_course.R
import com.example.gl_crash_course.citysettings.view.SettingsFragment
import com.example.gl_crash_course.databinding.ActivitySecondBinding
import com.example.gl_crash_course.weatherlist.view.FirstFragment
import com.example.gl_crash_course.weatherlist.view.WeatherAdapter
import com.example.gl_crash_course.viewmodel.VisitedViewModel
import com.example.gl_crash_course.weatherdetail.view.SecondFragment
import com.google.android.material.navigation.NavigationView

class SecondActivity : AppCompatActivity(), WeatherAdapter.VisitedInterface,
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var model: VisitedViewModel
    lateinit var binding: ActivitySecondBinding

    override fun isVisited(id: Int): Boolean {
        return model.isVisited(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        binding.navView.setNavigationItemSelectedListener(this)

        toggle.syncState()

        if (savedInstanceState == null) {
            createFragmentIfNeeded(null)
            binding.navView.setCheckedItem(R.id.nav_weather_list)
        }

        model = ViewModelProviders.of(this).get(VisitedViewModel::class.java)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        when (item.itemId) {
            R.id.nav_weather_list -> {
                fragmentTransaction
                    .replace(R.id.fragment_container, FirstFragment.newInstance())
                    .commit()
            }
            R.id.nav_city_setting -> {
                fragmentTransaction
                    .replace(R.id.fragment_container, SettingsFragment.newInstance())
                    .commit()
            }
            R.id.nav_setting -> {
                // TODO
            }
            else -> {
                fragmentTransaction
                    .replace(R.id.fragment_container, FirstFragment.newInstance())
                    .commit()
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, fragment, tag)
        fragmentTransaction.commitNow()
    }

    fun createFragmentIfNeeded(fragment: Fragment?, bundle: Bundle = Bundle.EMPTY) {
        if (fragment == null) {
            addFragment(FirstFragment.newInstance(), getString(R.string.tag_fragment_first))
        }
        if (fragment is FirstFragment && supportFragmentManager.findFragmentByTag(getString(R.string.tag_fragment_second)) == null) {
            val secondFragment = SecondFragment.newInstance()
            secondFragment.arguments = bundle
            addFragment(secondFragment, getString(R.string.tag_fragment_second))
        }
    }

    fun switchFragment(fragment: Fragment, bundle: Bundle = Bundle.EMPTY) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (fragment is FirstFragment) {
            val fragmentSecond = supportFragmentManager.findFragmentByTag(getString(R.string.tag_fragment_second))!!
            fragmentSecond.arguments = bundle
            fragmentTransaction.detach(fragment)
            fragmentTransaction.attach(fragmentSecond)
        }
        if (fragment is SecondFragment) {
            fragmentTransaction
                .replace(R.id.fragment_container, FirstFragment.newInstance())
        }

        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
