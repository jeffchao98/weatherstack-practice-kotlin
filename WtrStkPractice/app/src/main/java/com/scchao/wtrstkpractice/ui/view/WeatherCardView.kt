package com.scchao.wtrstkpractice.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.scchao.wtrstkpractice.R
import com.scchao.wtrstkpractice.data.model.Weather
import java.text.DecimalFormat

class WeatherCardView @JvmOverloads constructor(
    context: Context,
    val data: Weather,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        initView(context)
    }

    private var location: TextView? = null
    private var tempter: TextView? = null
    private var descript: TextView? = null
    private var statusImage: ImageView? = null

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.card_weather, this)
        location = view.findViewById(R.id.location_name)
        location?.text = data.location.name
        tempter = view.findViewById(R.id.tempter)
        tempter?.text = "${data.current.temperature}\u2103"
        descript = view.findViewById(R.id.description)
        val descrips = data.current.weather_descriptions
        if (descrips.size > 0) {
            descript?.text = descrips.get(0)
        }
        statusImage = view.findViewById(R.id.status_image)
        val images = data.current.weather_icons
        if (images.size > 0) {
            Glide.with(context).load(images.get(0)).into(statusImage)
        }

    }

}
