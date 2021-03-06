package com.example.andriinazar.weatherapp.api

import com.example.andriinazar.weatherapp.BuildConfig
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("current?NC&key=" + BuildConfig.WEATHER_API_KEY)
    fun getCurrentWeather(
            @Query("lat") lat: String,
            @Query("lon") lang: String,
            @Query("lang") language: String?) : Observable<Response<WeatherInfo>>

}