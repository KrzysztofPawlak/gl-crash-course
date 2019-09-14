package com.example.gl_crash_course.citysettings.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.ViewDataBinding
import com.example.gl_crash_course.databinding.CityListItemBinding
import com.example.gl_crash_course.db.model.CityEntry

class CityAdapter(
    private var dataSource: ArrayList<CityEntry>,
    private val callback: OnCityClickListener
) : BaseAdapter() {

    fun setData(dataSource: List<CityEntry>) {
        this.dataSource.clear()
        this.dataSource = dataSource as ArrayList<CityEntry>
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: CityListItemBinding
        if (convertView == null) {
            binding = CityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as CityListItemBinding
        }

        val holder = ViewHolder(binding)
        holder.bind(binding, dataSource[position], callback)

        return binding.root
    }

    class ViewHolder(val binding: ViewDataBinding) {
        fun bind(binding: ViewDataBinding, cityEntry: CityEntry, callback: OnCityClickListener) {
            when (binding) {
                is CityListItemBinding -> {
                    binding.idApi = cityEntry.api_id
                    binding.name = cityEntry.name
                    binding.listener = callback
                }
            }
        }
    }

    interface OnCityClickListener {
        fun onCityClick(id: Int)
    }
}