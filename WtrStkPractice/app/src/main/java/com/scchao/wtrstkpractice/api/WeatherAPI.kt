package com.scchao.wtrstkpractice.api

import com.scchao.wtrstkpractice.BuildConfig.WEATHER_TOKEN
import com.scchao.wtrstkpractice.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("/current?access_key=${WEATHER_TOKEN}")
    suspend fun queryCurrentWeather(@Query("query") key: String): Weather
}