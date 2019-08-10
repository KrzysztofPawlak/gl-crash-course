package com.example.gl_crash_course

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment

class SecondActivity : AppCompatActivity(), FirstFragment.OnFragmentInteractionListener {

    private var counterTransition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        if (savedInstanceState == null) {
            val fragment = FirstFragment.newInstance()
            replaceFragment(fragment, getString(R.string.tag_fragment_first))
        }

        counterTransition = intent.getIntExtra("counterTransition", 0)
    }

    override fun setTransitionCounter() {
        val firstFragment =
            supportFragmentManager.findFragmentByTag(getString(R.string.tag_fragment_first)) as FirstFragment?
        firstFragment?.updateTransitionCounter(counterTransition)
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("counterTransition", counterTransition)
        startActivity(intent)
        finish()
    }
}
