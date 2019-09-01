package com.example.gl_crash_course.forecastlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gl_crash_course.R
import com.example.gl_crash_course.repository.dao.WeatherEntry
import com.example.gl_crash_course.view.SecondActivity
import com.example.gl_crash_course.databinding.FragmentFirstBinding
import com.example.gl_crash_course.forecastlist.viewmodel.ForecastViewModel

class FirstFragment : Fragment(), ForecastAdapter.OnCityClickListener,
    ForecastAdapter.VisitedInterface {

    private lateinit var model: ForecastViewModel
    private lateinit var binding: FragmentFirstBinding
    private val adapter = ForecastAdapter(this, this)

    override fun isVisited(id: Int): Boolean {
        return (activity as SecondActivity).model.isVisited(id)
    }

    override fun onCityClick(id: Int) {
        (activity as SecondActivity).model.markAsVisited(id)
        val bundle = Bundle()
        bundle.putInt("id", id)

        (activity as SecondActivity).createFragmentIfNeeded(this, bundle)
        (activity as SecondActivity).switchFragment(this, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager = layoutManager

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model = ViewModelProviders.of(this).get(ForecastViewModel::class.java)

        model.getForecast().observe(viewLifecycleOwner, Observer<List<WeatherEntry>> { forecast ->
            adapter.updateForecast(forecast)
        })

        model.refresh.observe(viewLifecycleOwner, Observer {
            binding.firstFragment.isRefreshing = it
        })

        binding.firstFragment.setOnRefreshListener {
            model.refreshList()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FirstFragment()
    }

}
