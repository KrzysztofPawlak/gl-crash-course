package com.example.gl_crash_course.forecastlist

import androidx.recyclerview.widget.DiffUtil
import com.example.gl_crash_course.model.City

class CityDiffUtilCallback(private val cityList: List<City>, private val updatedList: List<City>) :
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
        val isTempTheSame = oldCity.main.temp.toString() == updatedCity.main.temp.toString()
        val isIconTheSame = oldCity.weather[0].icon == updatedCity.weather[0].icon

        return isNameTheSame && isTempTheSame && isIconTheSame
    }

}
