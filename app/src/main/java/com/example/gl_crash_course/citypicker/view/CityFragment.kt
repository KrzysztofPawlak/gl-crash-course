package com.example.gl_crash_course.citypicker.view

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
import com.example.gl_crash_course.citypicker.viewmodel.CityViewModel
import com.example.gl_crash_course.databinding.FragmentCityPickerBinding
import com.example.gl_crash_course.db.model.CityEntry
import com.example.gl_crash_course.db.model.SearchHistoryEntry

class CityFragment : Fragment(), CityAdapter.OnCityClickListener {

    private lateinit var binding: FragmentCityPickerBinding
    private var adapter: CityAdapter = CityAdapter(arrayListOf(), this)
    private lateinit var adapterHistoryList: ArrayAdapter<String>
    private lateinit var model: CityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_city_picker, container, false)

        binding.listView.adapter = adapter

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProviders.of(this).get(CityViewModel::class.java)
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
        })

        model.isSearchFinished.observe(this, Observer {
            if (it == true) {
                view.hideKeyboard()
            }
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
        fun newInstance() = CityFragment()
    }
}
