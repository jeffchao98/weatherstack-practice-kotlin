package com.scchao.wtrstkpractice.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.scchao.wtrstkpractice.R
import com.scchao.wtrstkpractice.data.model.Weather
import com.scchao.wtrstkpractice.ui.model.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    var location: TextView? = null
    var region: TextView? = null
    var coord: TextView? = null
    var icon: ImageView? = null
    var temper: TextView? = null

    private val detailViewModel: DetailViewModel by viewModel()

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private var weatherData: Weather? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inputStr = arguments?.getString("detail_prop")
        val root = inflater.inflate(R.layout.fragment_detail, container, false)
        location = root.findViewById(R.id.title_location)
        region = root.findViewById(R.id.title_region)
        coord = root.findViewById(R.id.title_coord)
        icon = root.findViewById(R.id.icon_image)
        temper = root.findViewById(R.id.title_temper)
        detailViewModel.liveLocation().observe(this, locationObserver)
        detailViewModel.liveRegion().observe(this, regionObserver)
        detailViewModel.liveCoord().observe(this, coordObserver)
        detailViewModel.liveImgUrl().observe(this, iconObserver)
        detailViewModel.liveTempText().observe(this, temperObserver)
        weatherData?.let {
            detailViewModel.setWeatherData(it)
        } ?: run {
            detailViewModel.setWeatherData(arguments?.getSerializable("weather_detail") as Weather?)
        }

        return root
    }

    private val locationObserver = Observer<String> { str ->
        location?.text = str
    }

    private val regionObserver = Observer<String> { str ->
        region?.text = str
    }

    private val coordObserver = Observer<String> { str ->
        coord?.text = str
    }

    private val temperObserver = Observer<String> { str ->
        temper?.text = str
    }

    private val iconObserver = Observer<String> { str ->
        str?.let {
            if (it.length > 0) {
                icon?.let { view ->
                    Glide.with(context).load(it).into(view)
                }
            }
        }
    }
}