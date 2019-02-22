package com.example.andriinazar.weatherapp

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andriinazar.weatherapp.api.CurrentWeather
import com.example.andriinazar.weatherapp.database.CityWeatherDataDB
import com.example.andriinazar.weatherapp.database.DatabaseTypeConverter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather_dialog_view.view.*

class WeatherDialog : AppCompatDialogFragment() {

    private var listener: OnDismissListener? = null

    interface OnDismissListener {
        fun onDismiss()
    }

    fun setDismissListener(dismissListener: OnDismissListener) {
        listener = dismissListener
    }

    var weatherInfo: CityWeatherDataDB? = null

    //  dialog view
    private val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.weather_dialog_view, null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return dialogView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherInfo = arguments?.getParcelable(WEATHER_INFO)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData(weatherInfo)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        listener?.onDismiss()
    }

    fun setupData(weatherInfo: CityWeatherDataDB?) {
        dialogView.tv_country_value.text = weatherInfo?.country_code
        dialogView.tv_city_value.text = weatherInfo?.city_name
        dialogView.tv_pressure_value.text = weatherInfo?.pres.toString() + " (mb)"
        dialogView.tv_temperature_value.text = weatherInfo?.temp.toString()
        val currentWeather: CurrentWeather? = weatherInfo?.weather?.let { DatabaseTypeConverter.fromJson(it) }
        val iconUrl = BuildConfig.BASE_ICON_URL + currentWeather?.icon + ".png"
        Picasso.get().load(iconUrl).into(dialogView.tv_weather_icon)
        dialogView.tv_weather_description.text = currentWeather?.description
        dialogView.tv_wind_speed_value.text = weatherInfo?.wind_spd.toString()
        dialogView.tv_wind_direction_value.text = weatherInfo?.wind_cdir_full
        dialogView.tv_last_update_value.text = weatherInfo?.ob_time
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create Alert Dialog with your custom view
        return AlertDialog.Builder(context!!)
                .setView(dialogView)
                .setNegativeButton("Close", null)
                .create()
    }


    companion object {
        const val  WEATHER_INFO = "weather"

        fun newInstance(info: CityWeatherDataDB?): WeatherDialog {
            val f = WeatherDialog()
            val args = Bundle()
            args.putParcelable(WEATHER_INFO, info)
            f.arguments = args
            return f
        }
    }
}