package com.example.andriinazar.weatherapp.database

import android.arch.persistence.room.*
import com.example.andriinazar.weatherapp.database.CityWeatherDataDB

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(weather: CityWeatherDataDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(weather: CityWeatherDataDB)

    @Delete
    fun delete(weather: CityWeatherDataDB)

    @Query("DELETE FROM weather_info")
    fun deleteAll()

    @Query("SELECT * FROM weather_info")
    fun getAllCitiesWeatherInfo() : List<CityWeatherDataDB>

    @Query("SELECT * FROM weather_info where ob_time < (select MAX(ob_time) from weather_info)")
    fun getLastWeatherInfo() : CityWeatherDataDB
}