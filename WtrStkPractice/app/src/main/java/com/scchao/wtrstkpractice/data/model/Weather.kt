package com.scchao.wtrstkpractice.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Weather(
    @SerializedName("request") var request: WeatherRequest,
    @SerializedName("location") var location: WeatherLocation,
    @SerializedName("current") var current: WeatherCurrent
) : Serializable