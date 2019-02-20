package com.example.andriinazar.weatherapp

import android.arch.persistence.room.*

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
}