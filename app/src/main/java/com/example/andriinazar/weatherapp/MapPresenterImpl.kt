package com.example.andriinazar.weatherapp

import android.content.Context
import android.content.SharedPreferences
import com.example.andriinazar.weatherapp.database.CityWeatherDataDB
import com.google.android.gms.maps.model.LatLng

class MapPresenterImpl (context: Context, presenter: IMapPresenter) {

    init {
        PreferencesHelper.init(context)
    }

    val mapPresenter = presenter

    private val weatherDataListener = object : IWeatherData {

        override fun onInternetConnectionError() {
            mapPresenter.onUpdateDataUnavaible()
        }

        override fun onDataReceive(weatherInfo: CityWeatherDataDB?) {
            PreferencesHelper.firstRun = true
            mapPresenter.onWeatherReceive(weatherInfo)
        }

        override fun onDataReceiveError(message: String?) {
            mapPresenter.onShowError(message)
        }

    }
    val dataManager by lazy {
        DataManager(context,weatherDataListener)
    }

    fun getWeatherData(coordinate: LatLng, language: String?) {
        if (!PreferencesHelper.firstRun && !dataManager.chakIntrnerConnction()) {
            mapPresenter.onDataUnavailable()
        } else{
            dataManager.getWeatherData(coordinate, language)
        }
    }

    fun getWeatherFromCache() {
        if (!PreferencesHelper.firstRun && !dataManager.chakIntrnerConnction()) {
            mapPresenter.onDataUnavailable()
        } else {
            dataManager.getSaveData()
        }

    }




}