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
            val fragment = FirstFragment.newInstance()
            replaceFragment(fragment, getString(R.string.tag_fragment_first))
        }

        model = ViewModelProviders.of(this).get(VisitedViewModel::class.java)
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
