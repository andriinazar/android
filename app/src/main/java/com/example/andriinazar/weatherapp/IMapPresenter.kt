package com.example.andriinazar.weatherapp

import com.example.andriinazar.weatherapp.database.CityWeatherDataDB

interface IMapPresenter {
    fun onUpdateDataUnavaible()
    fun onDataUnavailable()
    fun onWeatherReceive(weather: CityWeatherDataDB?)
    fun onShowError(error: String?)
}