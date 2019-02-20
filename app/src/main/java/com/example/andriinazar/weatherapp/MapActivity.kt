package com.example.andriinazar.weatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initGoogleMap()
    }

    private fun initListeners() {
        googleMap?.setOnMapClickListener {location ->
            val location = "Lat: " + location.latitude + " : " + "Long: " + location.longitude
            Toast.makeText(this@MapActivity, location, Toast.LENGTH_LONG).show()
        }

    }

    private fun initGoogleMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map
        initListeners()
        val sydney = LatLng(-34.0, 151.0)
        googleMap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        getWeather()
    }

    private fun getWeather() {
        ApiServiceBuilder.disposable =
                ApiServiceBuilder.weatherApiServise?.getCurrentWeather((-34.0).toString(),(151.0).toString(), "uk")
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(
                                { result ->
                                    var info: List<CityWeatherData> = result.data
                                    val i = 0
                                    //showResult(result.query.searchinfo.totalhits)
                                    },
                                { error -> Toast.makeText(this@MapActivity, error.message, Toast.LENGTH_LONG).show()}
                        )
    }

}
