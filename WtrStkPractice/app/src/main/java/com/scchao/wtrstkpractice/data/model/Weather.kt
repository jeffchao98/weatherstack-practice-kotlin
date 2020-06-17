package com.scchao.wtrstkpractice.data.model

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("request") val request: WeatherRequest,
    @SerializedName("location") val location: WeatherLocation,
    @SerializedName("current") val current: WeatherCurrent
)