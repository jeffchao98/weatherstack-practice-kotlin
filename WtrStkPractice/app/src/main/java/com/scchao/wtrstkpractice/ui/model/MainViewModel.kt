package com.scchao.wtrstkpractice.ui.model

import android.util.Log
import androidx.lifecycle.*
import com.scchao.wtrstkpractice.data.model.Weather
import com.scchao.wtrstkpractice.data.repository.KeyWordRepository
import com.scchao.wtrstkpractice.data.repository.WeatherDataRepository
import org.koin.dsl.module

val mainViewModel = module {
    factory { MainViewModel(get(), get()) }
}

class MainViewModel(
    private val weatherDataRepository: WeatherDataRepository,
    private val keyWordRepository: KeyWordRepository
) : ViewModel() {
    private var dataMap: MutableMap<String, Weather> = mutableMapOf()
    private var loggedKeys: MutableMap<String, Boolean> = mutableMapOf()

    private val preLoad = MutableLiveData<Boolean>()

    private val preloadData = preLoad.switchMap { inKeys ->
        liveData {
            var returnData: MutableList<Weather> = mutableListOf()
            returnData = assembleWeatherList(null, "")
            var inKeys = keyWordRepository.loadAll()
            inKeys?.let { keys ->
                keys.forEach { key ->
                    loggedKeys.put(key.keyword, true)
                    try {
                        val fetchRes = weatherDataRepository.queryWeather(key.keyword)
                        returnData = assembleWeatherList(fetchRes, key.keyword)
                    } catch (exception: Throwable) {
                        Log.i("Error", exception.message)
                    }
                }
            }
            emit(returnData)
        }
    }

    private val searchKey = MutableLiveData<String>()
    private val delWeather = MutableLiveData<Weather>()

    private val searchData = searchKey.switchMap { t1 ->
        liveData {
            var returnData: MutableList<Weather> = mutableListOf()
            dataMap.get(t1)?.let {
                returnData = assembleWeatherList(null, t1)
            } ?: run {
                try {
                    val fetchRes = weatherDataRepository.queryWeather(t1)
                    keyWordRepository.insert(t1)
                    returnData = assembleWeatherList(fetchRes, t1)
                } catch (exception: Throwable) {
                    Log.i("Error", exception.message)
                }
            }
            emit(returnData)
        }
    }

    private val removeData = delWeather.switchMap { t1 ->
        liveData {
            var returnData: MutableList<Weather> = mutableListOf()
            returnData = removeFromWeatherList(t1)
            keyWordRepository.remove(t1.searchKey)
            emit(returnData)
        }
    }

    fun preparedData(): LiveData<MutableList<Weather>> = searchData
    fun preloadData(): LiveData<MutableList<Weather>> = preloadData
    fun modifiedData(): LiveData<MutableList<Weather>> = removeData
    fun search(key: String) {
        searchKey.value = key
    }

    fun delete(weather: Weather) {
        delWeather.value = weather
    }

    fun preLoadKey() {
        preLoad.value = true
    }

    private fun assembleWeatherList(weather: Weather?, key: String): MutableList<Weather> {
        weather?.let {
            val location = it.location?.name ?: ""
            if (!location.isEmpty()) {
                it.searchKey = key
                dataMap.put(location, it)
            }
        }
        val mapKey = dataMap.keys.toList()
        var rtList = mutableListOf<Weather>()
        mapKey.forEach { key ->
            dataMap.get(key)?.let { item ->
                rtList.add(item)
            }
        }
        return rtList
    }

    private fun removeFromWeatherList(item: Weather?): MutableList<Weather> {
        item?.let {
            val location = it.location.name
            if (!location.isEmpty()) {
                dataMap.remove(location)
            }
        }
        val mapKey = dataMap.keys.toList()
        var rtList = mutableListOf<Weather>()
        mapKey.forEach { key ->
            dataMap.get(key)?.let { item ->
                rtList.add(item)
            }
        }
        return rtList
    }

}