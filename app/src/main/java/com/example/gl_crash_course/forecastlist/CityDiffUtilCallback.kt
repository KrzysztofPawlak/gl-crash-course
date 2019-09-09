package com.example.gl_crash_course.forecastlist

import androidx.recyclerview.widget.DiffUtil
import com.example.gl_crash_course.db.model.WeatherEntry

class CityDiffUtilCallback(private val cityList: List<WeatherEntry>, private val updatedList: List<WeatherEntry>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFoundedNearestForecast = cityList[oldItemPosition]
        val updatedFoundedNearestForecast = updatedList[newItemPosition]

        return oldFoundedNearestForecast.id == updatedFoundedNearestForecast.id
    }

    override fun getOldListSize(): Int {
        return cityList.size
    }

    override fun getNewListSize(): Int {
        return updatedList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCity = cityList[oldItemPosition]
        val updatedCity = updatedList[newItemPosition]

        val isNameTheSame = oldCity.name == updatedCity.name
        val isTempTheSame = oldCity.temperature == updatedCity.temperature
        val isIconTheSame = oldCity.icon == updatedCity.icon
        val isTimeTheSame = oldCity.refreshed == updatedCity.refreshed

        return isNameTheSame && isTempTheSame && isIconTheSame && isTimeTheSame
    }

}
