package com.example.andriinazar.weatherapp.database

import com.example.andriinazar.weatherapp.api.CurrentWeather
import com.google.gson.Gson

class DatabaseTypeConverter {
    companion object {
        private val gson = Gson()

        fun fromJson(string: String): CurrentWeather? {
            return gson.fromJson(string, CurrentWeather::class.java)
        }

        fun toJson(weather: CurrentWeather): String {
            return gson.toJson(weather)
        }

    }
}