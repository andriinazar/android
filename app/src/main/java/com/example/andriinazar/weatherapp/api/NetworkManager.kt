package com.example.andriinazar.weatherapp.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NetworkManager(context: Context) {

    val context = context

    private val networkInstance by lazy {
        ApiServiceBuilder.create()
    }

    fun getWeather(coordinate: LatLng, language: String?, responseCallback : INetworkResponse?) {
        ApiServiceBuilder.disposable =
                networkInstance?.getCurrentWeather(
                        coordinate.latitude.toString(),
                        coordinate.longitude.toString(),
                        language)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(
                                { result -> responseCallback?.onResponseSuccess(result)},
                                { error -> responseCallback?.onResponseError(error) }
                        )
    }

    fun checkInternetConnection() : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}