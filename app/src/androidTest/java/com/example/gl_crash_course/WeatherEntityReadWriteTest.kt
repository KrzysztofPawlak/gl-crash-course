package com.example.gl_crash_course

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gl_crash_course.db.AppDatabase
import com.example.gl_crash_course.db.dao.WeatherDao
import com.example.gl_crash_course.db.model.WeatherEntry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class WeatherEntityReadWriteTest {
    private lateinit var weatherDao: WeatherDao
    private lateinit var db: AppDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val idTestValue: Int = 0
    private val apiIdTestValue: Int = 1111111111
    private val nameTestValue: String = "foo"
    private val tempTestValue: String = "10"
    private val iconTestValue: String = "bar"
    private val refreshedTestValue: LocalDateTime = LocalDateTime.now()
    private val descTestValue: String = "baz"
    private val pressureTestValue: Double = 1000.0
    private val humidityTestValue: Int = 100
    private val windSpeedTestValue: Double = 100.0
    private val sunriseTestValue: Int = 100
    private val sunsetTestValue: Int = 100

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        weatherDao = db.weatherDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun should_have_one_entry_after_insert() {
        val weatherEntry = WeatherEntry(
            idTestValue,
            apiIdTestValue,
            nameTestValue,
            tempTestValue,
            iconTestValue,
            refreshedTestValue,
            descTestValue,
            pressureTestValue,
            humidityTestValue,
            windSpeedTestValue,
            sunriseTestValue,
            sunsetTestValue
        )
        weatherDao.insert(weatherEntry)
        val expectedSize = 1
        assertEquals(expectedSize, LiveDataTestUtil.newInstance().getValue(weatherDao.getAll()).size)
    }

}