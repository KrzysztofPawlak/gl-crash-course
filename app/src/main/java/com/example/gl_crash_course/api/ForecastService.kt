package com.example.gl_crash_course.api

import android.content.Context
import android.widget.Toast
import com.example.gl_crash_course.BuildConfig
import com.example.gl_crash_course.ForecastApiConst
import com.example.gl_crash_course.api.model.City
import com.example.gl_crash_course.api.model.Forecast
import com.example.gl_crash_course.api.model.HttpCacheInterceptor
import com.example.gl_crash_course.service.NetworkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ForecastService(var context: Context) {

    private var forecastApi: ForecastApi
    private val cacheSize: Long = 5 * 1024 * 1024
    private val cache: Cache = Cache(context.cacheDir, cacheSize)

    init {
        val client = OkHttpClient().newBuilder()
            .cache(cache)
            .addInterceptor(HttpCacheInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ForecastApiConst.API_PATH)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        forecastApi = retrofit.create(ForecastApi::class.java)
    }

    fun getWeatherById(id: String, callback: GetWeatherCallback) {
        forecastApi.getWeatherById(id, BuildConfig.OpenWeatherAppKey, ForecastApiConst.UNITS)
            .enqueue(object : Callback<City> {
                override fun onFailure(call: Call<City>, t: Throwable?) {}

                override fun onResponse(call: Call<City>, response: Response<City>) {
                    callback.onWeatherLoaded(response.body())
                }
            })
    }

    fun getWeatherByCityName(name: String, callback: GetWeatherByCityNameCallback) {
        forecastApi.getWeatherByCityName(name, BuildConfig.OpenWeatherAppKey, ForecastApiConst.UNITS)
            .enqueue(object : Callback<City> {
                override fun onFailure(call: Call<City>, t: Throwable?) {}

                override fun onResponse(call: Call<City>, response: Response<City>) {
                    callback.onWeatherLoaded(response.body())
                }
            })
    }

    fun getSetOfWeatherByIds(ids: String, callback: GetForecastCallback) {
        forecastApi.getSetOfWeatherByIds(ids, BuildConfig.OpenWeatherAppKey, ForecastApiConst.UNITS)
            .enqueue(object : Callback<Forecast> {
                override fun onFailure(call: Call<Forecast>, t: Throwable?) {
                    Toast.makeText(context, "fetch failure!!!", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                    if (response.message() == "has no network") {
                        Toast.makeText(context, "No Network!!!", Toast.LENGTH_LONG).show()
                    }
                    callback.onForecastLoaded(response.body())
                }
            })
    }

    interface GetForecastCallback {
        fun onForecastLoaded(forecast: Forecast?)
    }

    interface GetWeatherCallback {
        fun onWeatherLoaded(city: City?)
    }

    interface GetWeatherByCityNameCallback {
        fun onWeatherLoaded(city: City?)
    }
}
