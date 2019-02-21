package com.example.andriinazar.weatherapp.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.example.andriinazar.weatherapp.api.CityWeatherData
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "weather_info")
data class CityWeatherDataDB (
        @ColumnInfo(name = "app_temp") var app_temp: Double,
        @PrimaryKey
        @ColumnInfo(name = "city_name") var city_name: String,
        @ColumnInfo(name = "clouds") var clouds: Double,
        @ColumnInfo(name = "country_code") var country_code: String,
        @ColumnInfo(name = "datetime") var datetime: String,
        @ColumnInfo(name = "dewpt") var dewpt: Double,
        @ColumnInfo(name = "dhi") var dhi: Double,
        @ColumnInfo(name = "dni") var dni: Double,
        @ColumnInfo(name = "elev_angle") var elev_angle: Double,
        @ColumnInfo(name = "ghi") var ghi: Double,
        @ColumnInfo(name = "h_angle") var h_angle: Double,
        @ColumnInfo(name = "lat") var lat: Double,
        @ColumnInfo(name = "lon") var lon: Double,
        @ColumnInfo(name = "ob_time") var ob_time: Long,
        @ColumnInfo(name = "pod") var pod: String,
        @ColumnInfo(name = "precip") var precip: Double,
        @ColumnInfo(name = "pres") var pres: Double,
        @ColumnInfo(name = "rh") var rh: Double,
        @ColumnInfo(name = "slp") var slp: Double,
        @ColumnInfo(name = "snow") var snow: Double,
        @ColumnInfo(name = "solar_rad") var solar_rad: Double,
        @ColumnInfo(name = "state_code") var state_code: String,
        @ColumnInfo(name = "station") var station: String,
        @ColumnInfo(name = "sunrise") var sunrise: String,
        @ColumnInfo(name = "sunset") var sunset: String,
        @ColumnInfo(name = "temp") var temp: Double,
        @ColumnInfo(name = "timezone") var timezone: String,
        @ColumnInfo(name = "ts") var ts: Double,
        @ColumnInfo(name = "uv") var uv: Double,
        @ColumnInfo(name = "vis") var vis: Double,
        @TypeConverters(DatabaseTypeConverter::class)
        @ColumnInfo(name = "weather") var weather: String,
        @ColumnInfo(name = "wind_cdir") var wind_cdir: String,
        @ColumnInfo(name = "wind_cdir_full") var wind_cdir_full: String,
        @ColumnInfo(name = "wind_dir") var wind_dir: Double,
        @ColumnInfo(name = "wind_spd") var wind_spd: Double
) {
    constructor(data: CityWeatherData) : this(
            app_temp = data.app_temp,
            city_name = data.city_name,
            clouds = data.clouds,
            country_code = data.country_code,
            datetime =  data.datetime,
            dewpt = data.dewpt,
            dhi = data.dhi,
            elev_angle = data.elev_angle,
            ghi = data.ghi,
            dni = data.dni,
            h_angle = data.h_angle,
            lat = data.lat,
            lon = data.lon,
            ob_time = covertDateToTimestamp(data.ob_time),
            pod = data.pod,
            precip = data.precip,
            pres = data.pres,
            rh = data.rh,
            slp = data.slp,
            snow = data.snow,
            solar_rad = data.solar_rad,
            state_code = data.state_code,
            station = data.station,
            sunrise = data.sunrise,
            sunset = data.sunset,
            temp = data.temp,
            timezone = data.timezone,
            ts = data.ts,
            uv = data.uv,
            vis = data.vis,
            weather = DatabaseTypeConverter.toJson(data.weather),
            wind_cdir = data.wind_cdir,
            wind_cdir_full = data.wind_cdir_full,
            wind_dir = data.wind_dir,
            wind_spd = data.wind_spd
    )

    companion object {
        fun covertDateToTimestamp(dateString : String) : Long {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm");
            val date : Date = sdf.parse(dateString);
            return date.getTime()
        }
    }
}