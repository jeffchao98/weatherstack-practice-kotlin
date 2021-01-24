package com.scchao.wtrstkpractice.ui.model

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.scchao.wtrstkpractice.data.model.Weather
import com.scchao.wtrstkpractice.data.repository.WeatherDataRepository
import org.koin.dsl.module

val mainViewModel = module {
    factory { MainViewModel(get(), get()) }
}
const val SAVE_KEY = "Log_Search_Key"

class MainViewModel(
    private val weatherDataRepository: WeatherDataRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private var dataMap: MutableMap<String, Weather> = mutableMapOf()
    private var loggedKeys: MutableMap<String, Boolean> = mutableMapOf()

    private val preLoadKeys = MutableLiveData<MutableList<String>>()

    private val preloadData = preLoadKeys.switchMap { inKeys ->
        liveData {
            var returnData: MutableList<Weather> = mutableListOf()
            returnData = assembleWeatherList(null, "")
            inKeys?.let { keys ->
                keys.forEach { key ->
                    try {
                        val fetchRes = weatherDataRepository.queryWeather(key)
                        returnData = assembleWeatherList(fetchRes, key)
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
        preLoadKeys.value = loadSearchedKeys()
    }

    fun loadSearchedKeys(): MutableList<String> {
        var keys = mutableListOf<String>()
        var loadKeysStr = sharedPreferences.getString(SAVE_KEY, "[]")
        loadKeysStr?.let {
            val loadGson = Gson().fromJson(it, Array<String>::class.java)
            loadGson?.let {
                val loadList = loadGson.asList()
                loadList.forEach { string ->
                    loggedKeys.put(string, true)
                    keys.add(string)
                }
            }
        }
        return keys
    }

    private fun saveSearchedKeys(key: String) {
        loggedKeys.put(key, true)
        val keys = loggedKeys.keys.toList()
        val inStr = Gson().toJson(keys)
        val editor = sharedPreferences.edit()
        editor.putString(SAVE_KEY, inStr)
        editor.commit()
    }

    private fun delSearchedKeys(key: String) {
        loggedKeys.remove(key)
        val keys = loggedKeys.keys.toList()
        val inStr = Gson().toJson(keys)
        val editor = sharedPreferences.edit()
        editor.putString(SAVE_KEY, inStr)
        editor.commit()
    }

    private fun assembleWeatherList(weather: Weather?, key: String): MutableList<Weather> {
        weather?.let {
            val location = it.location?.name ?: ""
            if (!location.isEmpty()) {
                if (dataMap.get(location) == null) {
                    saveSearchedKeys(key)
                }
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
                delSearchedKeys(it.searchKey)
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