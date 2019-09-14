package com.example.gl_crash_course.api

import android.content.Context
import android.widget.Toast
import com.example.gl_crash_course.BuildConfig
import com.example.gl_crash_course.WeatherApiConst
import com.example.gl_crash_course.R
import com.example.gl_crash_course.api.model.City
import com.example.gl_crash_course.api.model.Forecast
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherService(var context: Context) {

    private var weatherApi: WeatherApi
    private val cacheSize: Long = 5 * 1024 * 1024
    private val cache: Cache = Cache(context.cacheDir, cacheSize)

    init {
        val client = OkHttpClient().newBuilder()
            .cache(cache)
            .addInterceptor(HttpNetworkOfflineInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(WeatherApiConst.API_PATH)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    fun getWeatherByCityName(name: String, callback: GetWeatherByCityNameCallback) {
        weatherApi.getWeatherByCityName(name, BuildConfig.OpenWeatherAppKey, WeatherApiConst.UNITS)
            .enqueue(object : Callback<City> {
                override fun onFailure(call: Call<City>, t: Throwable?) {
                    Toast.makeText(context, context.getString(R.string.fetch_toast_message), Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<City>, response: Response<City>) {
                    if (response.message() == context.getString(R.string.no_network_response_message)) {
                        Toast.makeText(context, context.getString(R.string.no_network_toast_message), Toast.LENGTH_LONG).show()
                    }
                    callback.onWeatherLoaded(response.body())
                }
            })
    }

    fun getSetOfWeatherByIds(ids: String, callback: GetForecastCallback) {
        weatherApi.getSetOfWeatherByIds(ids, BuildConfig.OpenWeatherAppKey, WeatherApiConst.UNITS)
            .enqueue(object : Callback<Forecast> {
                override fun onFailure(call: Call<Forecast>, t: Throwable?) {
                    Toast.makeText(context, context.getString(R.string.fetch_toast_message), Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                    if (response.message() == context.getString(R.string.no_network_response_message)) {
                        Toast.makeText(context, context.getString(R.string.no_network_toast_message), Toast.LENGTH_LONG).show()
                    }
                    callback.onForecastLoaded(response.body())
                }
            })
    }

    interface GetForecastCallback {
        fun onForecastLoaded(forecast: Forecast?)
    }

    interface GetWeatherByCityNameCallback {
        fun onWeatherLoaded(city: City?)
    }
}
