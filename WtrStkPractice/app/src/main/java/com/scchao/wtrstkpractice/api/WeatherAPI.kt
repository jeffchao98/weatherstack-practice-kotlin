package com.scchao.wtrstkpractice.api

import com.scchao.wtrstkpractice.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "a864d01e701726ba3deb77056c20e2d4"

interface WeatherAPI {
    @GET("/current?access_key=${API_KEY}")
    suspend fun queryCurrentWeather(@Query("query") key: String): Weather
}