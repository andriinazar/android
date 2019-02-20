package com.example.andriinazar.weatherapp

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceBuilder {

    companion object {

        // default request settings
        fun create(): WeatherApiService? {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getDefaultGsonConverter()))
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(getDefaultClient())
                    .build()

            return retrofit.create(WeatherApiService::class.java)
        }

        // for custom settings
        fun create(baseUrl: String?, jsonConverter: Gson?,  client: OkHttpClient?) : WeatherApiService? {
            val builder = Retrofit.Builder()
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            if (baseUrl == null) builder.baseUrl(BuildConfig.BASE_URL) else builder.baseUrl(baseUrl)

            if (jsonConverter == null) {
                builder.addConverterFactory(GsonConverterFactory.create(getDefaultGsonConverter()))
            } else {
                builder.addConverterFactory(GsonConverterFactory.create(jsonConverter))
            }

            if (client == null) {
                builder.client(getDefaultClient())
            } else {
                builder.client(client)
            }

            return builder.build().create(WeatherApiService::class.java)
        }

        private fun getDefaultGsonConverter() : Gson {
            return GsonBuilder()
                    .setLenient()
                    .create()
        }

        private fun getDefaultClient() : OkHttpClient {
            return OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build()
        }

        val weatherApiServise by lazy {
            ApiServiceBuilder.create()
        }

        var disposable: Disposable? = null
    }


}