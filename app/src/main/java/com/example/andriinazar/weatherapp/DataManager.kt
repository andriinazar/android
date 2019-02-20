package com.example.andriinazar.weatherapp

import android.content.Context
import com.example.andriinazar.weatherapp.api.CityWeatherData
import com.example.andriinazar.weatherapp.api.INetworkResponse
import com.example.andriinazar.weatherapp.api.NetworkManager
import com.example.andriinazar.weatherapp.api.WeatherInfo
import com.example.andriinazar.weatherapp.database.CityWeatherDataDB
import com.example.andriinazar.weatherapp.database.DatabaseManager
import com.example.andriinazar.weatherapp.database.DatabaseTypeConverter
import com.google.android.gms.maps.model.LatLng
import retrofit2.Response

class DataManager(context: Context, dataListener: IWeatherData?) {

    val weatherDataListener = dataListener

    private val networkManager by lazy {
        NetworkManager(context)
    }

    private val databaseManager by lazy {
        DatabaseManager(context)
    }

    fun getWeatherData (coordinate: LatLng, language: String?) {
        if (networkManager.checkInternetConnection()) {
            getDataFromServer(coordinate, language)
        } else {
            getDataFromDb()
        }
    }

    private fun getDataFromServer(coordinate: LatLng, language: String?) {
        networkManager.getWeather(coordinate, language, object: INetworkResponse{
            override fun onResponseError(error: Throwable) {
                weatherDataListener?.onDataReceiveError(error.message)
            }

            override fun onResponseSuccess(data: Response<WeatherInfo>?) {
                val responce : List<CityWeatherData>? = data?.body()?.data
                if (responce != null) {
                    for (info in responce) {
                        val weatherInfo = CityWeatherDataDB(info)
                        weatherDataListener?.onDataReceive(weatherInfo)
                        saveDataToDB(weatherInfo)
                    }
                }
            }
        })
    }

    private fun getDataFromDb() {
        val weatherInfo = databaseManager.getLastWeatherInfo()
        if (weatherInfo!= null) {
            weatherDataListener?.onDataReceive(weatherInfo)
        }
    }

    private fun saveDataToDB(info: CityWeatherDataDB) {
        databaseManager.saveWeatherInfo(info)
    }

    public fun getSaveData() {
        getDataFromDb()
    }

    public fun chakIntrnerConnction() : Boolean {
        return networkManager.checkInternetConnection()
    }
}