package com.example.andriinazar.weatherapp

import android.content.Context
import com.example.andriinazar.weatherapp.database.CityWeatherDataDB
import com.example.andriinazar.weatherapp.datamanager.DataManager
import com.example.andriinazar.weatherapp.datamanager.IWeatherData
import com.google.android.gms.maps.model.LatLng
import java.util.*


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
    private val dataManager by lazy {
        DataManager(context, weatherDataListener)
    }

    fun getWeatherData(coordinate: LatLng) {
        if (!PreferencesHelper.firstRun && !dataManager.checkInternetConnection()) {
            mapPresenter.onDataUnavailable()
        } else{
            dataManager.getWeatherData(coordinate, getDeviceCountryCode())
        }
    }

    fun getWeatherFromCache() {
        if (!PreferencesHelper.firstRun && !dataManager.checkInternetConnection()) {
            mapPresenter.onDataUnavailable()
        } else {
            // send data from db to activity
            dataManager.getDataFromDb()
            if (dataManager.checkInternetConnection()) {
                // update last data from server
                val lastWeatherData = dataManager.getLastWeatherInfo()
                if (lastWeatherData != null) {
                    val lastCoordinates = LatLng(lastWeatherData.lat, lastWeatherData.lon)
                    getWeatherData(lastCoordinates)
                }
            } else {
                mapPresenter.onUpdateDataUnavaible()
            }

            clearOldData()
        }
    }

    private fun clearOldData() {
        dataManager.clearOldData()
    }

    private fun getDeviceCountryCode() : String {
        return Locale.getDefault().country
    }
}