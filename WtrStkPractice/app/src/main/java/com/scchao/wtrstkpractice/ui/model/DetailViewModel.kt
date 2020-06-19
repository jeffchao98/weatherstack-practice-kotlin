package com.scchao.wtrstkpractice.ui.model

import androidx.lifecycle.*
import com.scchao.wtrstkpractice.data.model.Weather
import org.koin.dsl.module

val detailViewModel = module {
    factory { DetailViewModel() }
}

class DetailViewModel () : ViewModel() {
    private val weather = MutableLiveData<Weather>()

    private val iconUrl = weather.switchMap {data ->
        liveData {
            var imgUrl = ""
            data?.let {
                it.current?.let {current ->
                    if(current.weather_icons.size > 0) {
                        imgUrl = current.weather_icons.get(0)
                    }
                }
            }
            emit(imgUrl)
        }
    }
    fun liveImgUrl(): LiveData<String> = iconUrl

    private val weatherDescribe = weather.switchMap {data ->
        liveData {
            var wtDes = ""
            data?.let {
                it.current?.let {current ->
                    if(current.weather_descriptions.size > 0) {
                        wtDes = current.weather_descriptions.get(0)
                    }
                }
            }
            emit(wtDes)
        }
    }
    fun liveWeatherDescribe(): LiveData<String> = weatherDescribe

    private val temp = weather.switchMap {data ->
        liveData {
            var temp = 0
            data?.let {
                it.current?.let {current ->
                    temp = current.temperature
                }
            }
            emit("${temp}℃")
        }
    }
    fun liveTempText(): LiveData<String> = temp

    private val location = weather.switchMap {data ->
        liveData {
            var locationText = ""
            data?.let {
                it.location?.let {location ->
                    locationText = location.name
                }
            }
            emit(locationText)
        }
    }
    fun liveLocation(): LiveData<String> = location

    private val region = weather.switchMap {data ->
        liveData {
            var regionText = ""
            data?.let {
                it.location?.let {location ->
                    regionText = "${location.country}, ${location.region}"
                }
            }
            emit(regionText)
        }
    }
    fun liveRegion(): LiveData<String> = region

    private val coord = weather.switchMap {data ->
        liveData {
            var coordText = ""
            data?.let {
                it.location?.let {location ->
                    coordText = "${location.lon}, ${location.lat}"
                }
            }
            emit(coordText)
        }
    }
    fun liveCoord(): LiveData<String> = coord

    fun setWeatherData(data: Weather?) {
        data?.let {
            weather.value = it
        }
    }
}