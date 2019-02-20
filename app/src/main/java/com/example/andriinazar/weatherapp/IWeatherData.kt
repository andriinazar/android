package com.example.andriinazar.weatherapp

import com.example.andriinazar.weatherapp.database.CityWeatherDataDB

interface IWeatherData {
    fun onInternetConnectionError()
    fun onDataReceive(weatherInfo: CityWeatherDataDB?)
    fun onDataReceiveError(message: String?)
}