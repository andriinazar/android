package com.example.andriinazar.weatherapp.datamanager

import android.content.Context
import com.example.andriinazar.weatherapp.api.CityWeatherData
import com.example.andriinazar.weatherapp.api.INetworkResponse
import com.example.andriinazar.weatherapp.api.NetworkManager
import com.example.andriinazar.weatherapp.api.WeatherInfo
import com.example.andriinazar.weatherapp.database.CityWeatherDataDB
import com.example.andriinazar.weatherapp.database.DatabaseManager
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
                val response : List<CityWeatherData>? = data?.body()?.data
                if (response != null) {
                    for (info in response) {
                        val weatherInfo = CityWeatherDataDB(info)
                        weatherDataListener?.onDataReceive(weatherInfo)
                        saveDataToDB(weatherInfo)
                    }
                }
            }
        })
    }

   fun getDataFromDb() {
        val weatherInfo: CityWeatherDataDB? = databaseManager.getLastWeatherInfo()
        if (weatherInfo != null) {
            weatherDataListener?.onDataReceive(weatherInfo)
        }
    }

    private fun saveDataToDB(info: CityWeatherDataDB) {
        databaseManager.saveWeatherInfo(info)
    }

    fun getLastWeatherInfo() : CityWeatherDataDB? {
         return databaseManager.getLastWeatherInfo()
    }

    fun checkInternetConnection() : Boolean {
        return networkManager.checkInternetConnection()
    }

    fun clearOldData() {
        val cacheWeather = databaseManager.getAllData()
        if (cacheWeather != null) {
            val cacheSize = cacheWeather.size
            if (cacheSize > 50) {
                for (i in 0..cacheSize - 50 ) {
                    databaseManager.getOldWeatherInfo()?.let { databaseManager.deleteWeatherInfo(it) }
                }
            }
        }
    }
}