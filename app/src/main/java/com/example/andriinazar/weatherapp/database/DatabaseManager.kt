package com.example.andriinazar.weatherapp.database

import android.content.Context

class DatabaseManager (context: Context) {

    private val weatherDao by lazy {
        WeatherDatabase.getInstance(context)?.getWeatherDao()
    }

    fun getLastWeatherInfo(): CityWeatherDataDB? {
        return weatherDao?.getLastWeatherInfo()
    }

    fun saveWeatherInfo(info : CityWeatherDataDB) {
        weatherDao?.save(info)
    }

    fun updateWeatherInfo(info: CityWeatherDataDB) {
        weatherDao?.update(info)
    }

    fun getAllData(): List<CityWeatherDataDB>? {
        return weatherDao?.getAllCitiesWeatherInfo()
    }

    fun deleteWeatherInfo(info: CityWeatherDataDB) {
        weatherDao?.delete(info)
    }

    fun getOldWeatherInfo(): CityWeatherDataDB? {
        return weatherDao?.getOldWeatherInfo()
    }

}