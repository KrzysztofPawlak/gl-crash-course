package com.example.gl_crash_course.citysettings.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.gl_crash_course.ForecastApiConst

import com.example.gl_crash_course.R
import com.example.gl_crash_course.databinding.FragmentSettingsBinding
import com.example.gl_crash_course.view.SecondActivity

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        val list = ArrayList(ForecastApiConst.PL_CITIES_IDS.keys)
        adapter = ArrayAdapter(context, R.layout.city_list_item, list)
        binding.listView.adapter = adapter

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as SecondActivity).hideFloatingActionButton()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}
