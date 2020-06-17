package com.scchao.wtrstkpractice.data.model

import com.google.gson.annotations.SerializedName

data class WeatherLocation(
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
    @SerializedName("region") val region: String,
    @SerializedName("lat") val lat: String,
    @SerializedName("lon") val lon: String,
    @SerializedName("timezone_id") val timezone_id: String,
    @SerializedName("localtime") val localtime: String,
    @SerializedName("localtime_epoch") val localtime_epoch: String,
    @SerializedName("utc_offset") val utc_offset: String
)