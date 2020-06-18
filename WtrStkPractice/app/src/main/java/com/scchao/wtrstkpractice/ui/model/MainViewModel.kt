package com.scchao.wtrstkpractice.ui.model

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.scchao.wtrstkpractice.data.model.Weather
import com.scchao.wtrstkpractice.data.repository.WeatherDataRepository
import org.koin.dsl.module

val mainViewModel = module {
    factory { MainViewModel(get(), get()) }
}

class MainViewModel (
    private val weatherDataRepository: WeatherDataRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private var dataMap: MutableMap<String, Weather> = mutableMapOf()

    private val searchKey = MutableLiveData<String>()

    private val searchData = searchKey.switchMap { t1 ->
        liveData {
            var returnData: MutableList<Weather> = mutableListOf()
            dataMap.get(t1)?.let {
                returnData = assembleWeatherList(null)
            } ?: run {
                try {
                    val fetchRes = weatherDataRepository.queryWeather(t1)
                    returnData = assembleWeatherList(fetchRes)
                } catch (exception: Throwable) {
                    Log.i("Error", exception.message)
                }
            }
            emit(returnData)
        }
    }
    fun preparedData(): LiveData<MutableList<Weather>> = searchData
    fun search(key: String) {
        searchKey.value = key
//        weatherDataRepository.queryWeather(key)
    }
    private suspend fun assembleWeatherList(weather: Weather?): MutableList<Weather> {
        weather?.let {
            val location = it.location?.name ?: ""
            if(!location.isEmpty()) {
                dataMap.put(location, it)
            }
        }
        val mapKey = dataMap.keys.toList()
        var rtList = mutableListOf<Weather>()
        mapKey.forEach {key ->
            dataMap.get(key)?.let {item ->
                rtList.add(item)
            }
        }
        return rtList
    }

}