package com.example.gl_crash_course

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.gl_crash_course.memberslist.FirstFragment

class SecondActivity : AppCompatActivity(), VisitedInterface {

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

        model = ViewModelProviders.of(this).get(VisitedViewModel::class.java)
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
