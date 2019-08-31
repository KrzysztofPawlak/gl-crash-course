package com.example.gl_crash_course.forecastlist.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gl_crash_course.databinding.ListItemBinding
import com.example.gl_crash_course.forecastlist.CityDiffUtilCallback
import com.example.gl_crash_course.model.City
import kotlinx.android.synthetic.main.list_item.view.*

class ForecastAdapter(private val callback: OnCityClickListener, private val callbackVisited: VisitedInterface) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private var forecastList: List<City> = ArrayList()

    interface OnCityClickListener {
        fun onCityClick(id: Int)
    }

    interface VisitedInterface {
        fun isVisited(id: Int): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater)
        binding.listItem.minimumHeight = parent.measuredHeight / 3

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < itemCount) {
            holder.bind(holder.binding, forecastList[position], callback)
            holder.binding.executePendingBindings()

            if (callbackVisited.isVisited(forecastList[position].id)) {
                holder.itemView.list_item.setBackgroundColor(Color.GRAY)
            }
        }
    }

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(binding: ViewDataBinding, city: City, callback: OnCityClickListener) {
            when (binding) {
                is ListItemBinding -> {
                    binding.id = city.id
                    binding.name = city.name
                    binding.temp = city.main.temp.toString()
                    binding.icon = city.weather[0].icon
                    binding.listener = callback
                }
            }
        }
    }

    fun updateForecast(updatedList: List<City>) {
        val result = DiffUtil.calculateDiff(
            CityDiffUtilCallback(
                forecastList,
                updatedList
            )
        )
        forecastList = updatedList.toMutableList()
        result.dispatchUpdatesTo(this)
    }
}