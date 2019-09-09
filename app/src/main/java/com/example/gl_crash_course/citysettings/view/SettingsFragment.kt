package com.example.gl_crash_course.citysettings.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gl_crash_course.R
import com.example.gl_crash_course.api.model.City
import com.example.gl_crash_course.citysettings.viewmodel.SettingsViewModel
import com.example.gl_crash_course.databinding.FragmentSettingsBinding
import com.example.gl_crash_course.repository.dao.CityEntry
import com.example.gl_crash_course.repository.dao.SearchHistoryEntry
import com.example.gl_crash_course.view.SecondActivity

class SettingsFragment : Fragment(), CityAdapter.OnCityClickListener {

    private lateinit var binding: FragmentSettingsBinding
    private var adapter: CityAdapter = CityAdapter(arrayListOf(), this)
    private lateinit var adapterHistoryList: ArrayAdapter<String>
    private lateinit var model: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.listView.adapter = adapter

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as SecondActivity).hideFloatingActionButton()

        model = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        binding.model = model

        model.mediatorCityLiveData.observe(this, Observer<List<CityEntry>> {
            adapter.setData(it)
            adapter.notifyDataSetChanged()
        })

        var listCity = arrayListOf<String>()
        model.mediatorCityLiveData.observe(this, Observer<List<CityEntry>> {
            listCity.clear()
            listCity.addAll(it.map { city -> city.name + " - " + city.country } as ArrayList<String>)
            adapter.notifyDataSetChanged()
        })

        var listSearchHistory = arrayListOf<String>()
        adapterHistoryList = ArrayAdapter(context, R.layout.search_history_list_item, listSearchHistory)
        binding.listSearchHistoryView.adapter = adapterHistoryList
        model.mediatorSearchHistoryLiveData.observe(this, Observer<List<SearchHistoryEntry>> {
            listSearchHistory.clear()
            listSearchHistory.addAll(it.map { city -> city.name + " - " + city.searchDate } as ArrayList<String>)
            adapterHistoryList.notifyDataSetChanged()
        })

        model.searchResult.observe(this, Observer<City> {
            binding.tvResult.text = it.name + " - " + it.sys.country
            view.hideKeyboard()
        })
    }

    override fun onCityClick(api_id: Int) {
        model.deleteCityFromList(api_id)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}
