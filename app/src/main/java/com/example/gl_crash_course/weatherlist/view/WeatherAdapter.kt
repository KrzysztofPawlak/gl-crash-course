package com.example.gl_crash_course.weatherlist.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gl_crash_course.WeatherApiConst.ADAPTER_SIZE_DIVIDE
import com.example.gl_crash_course.db.model.WeatherEntry
import com.example.gl_crash_course.databinding.ListItemBinding
import com.example.gl_crash_course.weatherlist.CityDiffUtilCallback
import kotlinx.android.synthetic.main.list_item.view.*

class WeatherAdapter(private val callback: OnCityClickListener, private val callbackVisited: VisitedInterface) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var forecastList: List<WeatherEntry> = ArrayList()

    interface OnCityClickListener {
        fun onCityClick(id: Int)
    }

    interface VisitedInterface {
        fun isVisited(id: Int): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater)
        binding.listItem.minimumHeight = parent.measuredHeight / ADAPTER_SIZE_DIVIDE
        binding.listItem.linear_layout_item_card.minimumHeight = parent.measuredHeight / ADAPTER_SIZE_DIVIDE

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
        fun bind(binding: ViewDataBinding, weatherEntry: WeatherEntry, callback: OnCityClickListener) {
            when (binding) {
                is ListItemBinding -> {
                    binding.id = weatherEntry.id
                    binding.idApi = weatherEntry.api_id // TODO: usunać
                    binding.name = weatherEntry.name
                    binding.temp = weatherEntry.temperature
                    binding.icon = weatherEntry.icon
                    binding.refreshed = weatherEntry.refreshed.toString()
                    binding.listener = callback
                }
            }
        }
    }

    fun updateForecast(updatedList: List<WeatherEntry>) {
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