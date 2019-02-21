package com.example.andriinazar.weatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.andriinazar.weatherapp.database.CityWeatherDataDB
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    private var presenterImpl: MapPresenterImpl? = null

    private var weatherDialog: WeatherDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initGoogleMap()
    }

    override fun onStart() {
        super.onStart()
        presenterImpl = MapPresenterImpl(this@MapActivity, presenter)
        presenterImpl?.getWeatherFromCache()
    }

    private var presenter = object : IMapPresenter {
        override fun onUpdateDataUnavaible() {
            showInternetConnectionError()
        }

        override fun onDataUnavailable() {
            showGettingDataErrorDialog()
        }

        override fun onWeatherReceive(weather: CityWeatherDataDB?) {

        }

        override fun onShowError(error: String?) {
            Toast.makeText(this@MapActivity, error, Toast.LENGTH_LONG).show()
        }

    }

    private fun initListeners() {
        googleMap?.setOnMapClickListener {location ->
            presenterImpl?.getWeatherData(location)
        }

    }

    private fun initGoogleMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map
        initListeners()
        val lviv = LatLng(49.83, 24.02)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(lviv))
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(8f))
    }

    private fun showInternetConnectionError() {
        Toast.makeText(this@MapActivity, getString(R.string.internet_connection_error), Toast.LENGTH_LONG).show()
    }

    private fun showGettingDataErrorDialog() {
        val builder = AlertDialog.Builder(this@MapActivity)
        builder.setTitle(getString(R.string.data_getting_error))

        builder.setMessage(getString(R.string.data_getting_message))
        builder.setPositiveButton(getString(R.string.close_app)){_, _ ->
            finish()
        }
        builder.setCancelable(false)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showWeaterDialog(weather: CityWeatherDataDB?) {
        if (weatherDialog == null) {
            weatherDialog = WeatherDialog(this@MapActivity, weather)
        }
    }


}
