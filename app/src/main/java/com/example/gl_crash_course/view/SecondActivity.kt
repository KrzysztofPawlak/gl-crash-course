package com.example.gl_crash_course.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.gl_crash_course.R
import com.example.gl_crash_course.citysettings.view.SettingsFragment
import com.example.gl_crash_course.forecastlist.view.FirstFragment
import com.example.gl_crash_course.forecastlist.view.ForecastAdapter
import com.example.gl_crash_course.viewmodel.VisitedViewModel
import com.example.gl_crash_course.weatherdetail.view.SecondFragment
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity(), ForecastAdapter.VisitedInterface {

    lateinit var model: VisitedViewModel

    override fun isVisited(id: Int): Boolean {
        return model.isVisited(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        if (savedInstanceState == null) {
            createFragmentIfNeeded(null)
        }

        fab.setOnClickListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            var settingsFragment = SettingsFragment.newInstance()
            fragmentTransaction.replace(R.id.fragment_container, settingsFragment)
            fragmentTransaction.commit()
        }

        model = ViewModelProviders.of(this).get(VisitedViewModel::class.java)
    }

    fun hideFloatingActionButton() {
        fab.hide()
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
            fragmentTransaction.detach(fragment)
            fragmentTransaction.attach(supportFragmentManager.findFragmentByTag(getString(R.string.tag_fragment_first))!!)
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
