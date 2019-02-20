package com.example.andriinazar.weatherapp.database

import com.example.andriinazar.weatherapp.api.Weather
import com.google.gson.Gson

class DatabaseTypeConverter {
    companion object {
        private val gson = Gson()

        fun fromJson(string: String): Weather? {
            return gson.fromJson(string, Weather::class.java)
        }

        fun toJson(weather: Weather): String {
            return gson.toJson(weather)
        }

    }
}