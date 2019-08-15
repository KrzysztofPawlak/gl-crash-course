package com.example.gl_crash_course

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var counterTransition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartSecondActivity.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            counterTransition += 1
            intent.putExtra("counterTransition", counterTransition)
            startActivity(intent)
            finish()
        }

        counterTransition = intent.getIntExtra("counterTransition", 0)
    }
}
