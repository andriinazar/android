package com.example.andriinazar.weatherapp

import android.content.Context

class DatabaseMenager (context: Context) {

    val weatherDao by lazy {
        WeatherDatabase.getInstance(context)?.getWeatherDao()
    }



}