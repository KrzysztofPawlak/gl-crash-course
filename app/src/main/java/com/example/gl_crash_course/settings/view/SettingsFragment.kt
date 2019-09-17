package com.example.gl_crash_course.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.gl_crash_course.R
import com.example.gl_crash_course.SharedPreferencesManager
import com.example.gl_crash_course.WeatherApiConst.MINUTES_IN_HOUR
import com.example.gl_crash_course.databinding.FragmentSettingsBinding
import com.example.gl_crash_course.view.SecondActivity

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var model: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.numberPickerHours.minValue = 0
        binding.numberPickerHours.maxValue = 23
        binding.numberPickerMinutes.minValue = 0
        binding.numberPickerMinutes.maxValue = 59

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        binding.model = model

        val sharedPreferencesManager = SharedPreferencesManager((activity as SecondActivity))
        model.languageIdValue = sharedPreferencesManager.getValueString("lang")

        val interval = sharedPreferencesManager.getValueInt("interval")
        val hours = calculateHours(interval!!)
        val minutes = calculateRemainsMinutesAfterSubstractFullHours(interval, hours)
        model.intervalHours.value = hours
        model.intervalMinutes.value = minutes

        binding.btnSave.setOnClickListener {
            sharedPreferencesManager.save("lang", model.languageIdValue!!)
            sharedPreferencesManager.setLanguage()
            (activity as SecondActivity).reloadActivity()

            val interval = model.intervalHours.value!! * MINUTES_IN_HOUR + model.intervalMinutes.value!!
            sharedPreferencesManager.save("interval", interval)

            (activity as SecondActivity).refreshFragment(this)

            Toast.makeText(context, this.getString(R.string.settings_saved_toast_message), Toast.LENGTH_LONG).show()
        }
    }

    private fun calculateHours(minutes: Int): Int {
        return minutes.div(MINUTES_IN_HOUR)
    }

    private fun calculateRemainsMinutesAfterSubstractFullHours(interval: Int, hours: Int): Int {
        return interval - hours * MINUTES_IN_HOUR
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}
