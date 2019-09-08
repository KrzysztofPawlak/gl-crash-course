package com.example.gl_crash_course.forecastlist.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.gl_crash_course.ForecastApiConst
import com.example.gl_crash_course.ForecastApiConst.ADAPTER_LIST_SIZE
import com.example.gl_crash_course.ForecastApiConst.UPDATE_TIME
import com.example.gl_crash_course.repository.dao.WeatherEntry
import com.example.gl_crash_course.api.model.Forecast
import com.example.gl_crash_course.repository.ForecastRepository
import com.example.gl_crash_course.api.ForecastService
import java.time.*
import java.time.temporal.ChronoUnit
import java.util.concurrent.atomic.AtomicInteger

class ForecastViewModel(application: Application) : AndroidViewModel(application),
    ForecastService.GetForecastCallback, ForecastRepository.DbOperationCallback {

    private val forecastRepository = ForecastRepository(application)
    private var forecastService: ForecastService = ForecastService(application)

    private var counterCurrentOperationOnDb = AtomicInteger()
    var refresh = MutableLiveData<Boolean>()

    private val mutableForecast = MediatorLiveData<List<WeatherEntry>>()

    init {
        var dataFromDb = forecastRepository.allWeather
        mutableForecast.addSource(dataFromDb) { result: List<WeatherEntry>? ->
            result?.let {
                mutableForecast.value = it
                fetchIfNeeded(it)
            }
        }
    }

    fun getForecast(): MediatorLiveData<List<WeatherEntry>> {
        return mutableForecast
    }

    fun refreshList() {
        getData()
    }

    private fun fetchIfNeeded(it: List<WeatherEntry>) {
        var allowedCurrentOperations = 0
        if (counterCurrentOperationOnDb.get() != allowedCurrentOperations) {
            return
        }

        var isOutdated = checkCurrentEntriesAreOutdated()
        if (it.size < ADAPTER_LIST_SIZE || isOutdated) {
            getData()
        }
    }

    private fun insertWeather(weatherEntry: WeatherEntry) {
        forecastRepository.insert(weatherEntry, this)
    }

    private fun updateWeather(weatherEntry: WeatherEntry) {
        forecastRepository.update(weatherEntry, this)
    }

    private fun getData() {
        var ids: String = ForecastApiConst.PL_CITIES_IDS.map { entry -> entry.value }.joinToString(",")
        forecastService.getSetOfWeatherByIds(ids, this)
    }

    override fun onForecastLoaded(forecast: Forecast?) {

        val list = forecast!!.list.map {
            WeatherEntry(0, it.id, it.name, it.main.temp.toString(), it.weather[0].icon, LocalDateTime.now())
        }

        list.stream()
            .filter { isAlreadyExists(it.api_id) }
            .forEach {
                counterCurrentOperationOnDb.getAndIncrement()
                updateWeather(it)
            }

        list.stream()
            .filter { !isAlreadyExists(it.api_id) }
            .forEach {
                counterCurrentOperationOnDb.getAndIncrement()
                insertWeather(it)
            }

        refresh.value = false
    }

    private fun checkCurrentEntriesAreOutdated(): Boolean {
        return mutableForecast.value!!.any {
            ChronoUnit.MINUTES.between(it.refreshed.toInstant(OffsetDateTime.now().offset), Instant.now()) > UPDATE_TIME
        }
    }

    private fun isAlreadyExists(searchingId: Int): Boolean {
        return mutableForecast.value!!.any { it.api_id == searchingId }
    }

    override fun onFinishDb() {
        counterCurrentOperationOnDb.getAndDecrement()
    }

}