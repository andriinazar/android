package com.example.andriinazar.weatherapp

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.andriinazar.weatherapp.api.CurrentWeather
import com.example.andriinazar.weatherapp.database.CityWeatherDataDB
import com.example.andriinazar.weatherapp.database.DatabaseTypeConverter
import kotlinx.android.synthetic.main.weather_dialog_view.view.*

class WeatherDialog(context: Context, val info: CityWeatherDataDB?) : Dialog(context) {

    init {
        setupData(info)
    }

    //  dialog view
    private val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.weather_dialog_view, null)
    }

    public fun setupData(weatherInfo: CityWeatherDataDB?) {
        dialogView.tv_country_value.text = weatherInfo?.city_name
        dialogView.tv_city_value.text = weatherInfo?.city_name
        dialogView.tv_pressure_value.text = weatherInfo?.pres.toString() + " (mb)"
        dialogView.tv_temperature_value.text = weatherInfo?.temp.toString()
        val currentWeather: CurrentWeather? = weatherInfo?.weather?.let { DatabaseTypeConverter.fromJson(it) }
        dialogView.tv_weather_icon.text = currentWeather?.icon
        dialogView.tv_weather_description.text = currentWeather?.description

    }
}