package com.scchao.wtrstkpractice.data.model

import com.google.gson.annotations.SerializedName

data class WeatherCurrent(
    @SerializedName("observation_time") val observation_time: String,
    @SerializedName("temperature") val temperature: Int,
    @SerializedName("weather_code") val weather_code: Int,
    @SerializedName("weather_icons") val weather_icons: Array<String>,
    @SerializedName("weather_descriptions") val weather_descriptions: Array<String>,
    @SerializedName("wind_speed") val wind_speed: Int,
    @SerializedName("wind_degree") val wind_degree: Int,
    @SerializedName("wind_dir") val wind_dir: String,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("feelslike") val feelslike: Int
)