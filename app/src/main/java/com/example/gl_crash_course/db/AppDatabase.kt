package com.example.gl_crash_course.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gl_crash_course.db.dao.*
import com.example.gl_crash_course.db.model.CityEntry
import com.example.gl_crash_course.db.model.SearchHistoryEntry
import com.example.gl_crash_course.db.model.WeatherEntry

@Database(entities = [WeatherEntry::class, CityEntry::class, SearchHistoryEntry::class], version = 9)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun cityDao(): CityDao
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "weather.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }
    }
}