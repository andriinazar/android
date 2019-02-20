package com.example.andriinazar.weatherapp.api

import com.example.andriinazar.weatherapp.api.CityWeatherData

data class WeatherInfo(
        val count: Int,
        val data: List<CityWeatherData>
)