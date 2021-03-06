package com.example.andriinazar.weatherapp.api

import retrofit2.Response

interface INetworkResponse {
    fun onResponseError(error: Throwable)
    fun onResponseSuccess(data: Response<WeatherInfo>?)
}