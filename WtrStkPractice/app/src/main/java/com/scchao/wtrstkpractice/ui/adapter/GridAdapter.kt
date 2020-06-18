package com.scchao.wtrstkpractice.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.scchao.wtrstkpractice.data.model.Weather
import com.scchao.wtrstkpractice.ui.view.WeatherCardView

class GridAdapter @JvmOverloads constructor(
    var context: Context,
    var weatherList: List<Weather>
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var weatherItem = weatherList[position]
        return WeatherCardView(context, weatherItem)
    }

    override fun getItem(position: Int): Any {
        return weatherList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return weatherList.size
    }
}