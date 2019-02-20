package com.example.andriinazar.weatherapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(CityWeatherDataDB::class), version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao() : WeatherDao

    // Thread save DB instance
    companion object {
        private var WeatherDbInstance: WeatherDatabase? = null
        fun getInstance(context: Context): WeatherDatabase? {
            if (WeatherDbInstance == null) {
                synchronized(WeatherDatabase::class) {
                    WeatherDbInstance = Room.databaseBuilder(context.applicationContext,
                            WeatherDatabase::class.java, "weather.db")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return WeatherDbInstance
        }
    }
}

