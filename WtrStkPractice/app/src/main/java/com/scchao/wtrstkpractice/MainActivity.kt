package com.scchao.wtrstkpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.scchao.wtrstkpractice.data.model.Weather
import com.scchao.wtrstkpractice.ui.fragment.DetailFragment
import com.scchao.wtrstkpractice.ui.fragment.ListFragment

class MainActivity : AppCompatActivity(), ListFragment.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listFragment = ListFragment()

        listFragment.arguments = intent.extras
        listFragment.setOnItemClickListener(this)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, listFragment).commit()
    }

    override fun onItemClick(weather: Weather) {
        switchToDetail(weather)
    }

    fun switchToDetail(weather: Weather) {
        val newFragment = DetailFragment()
        var args = Bundle()
        args.putSerializable("weather_detail", weather)
        newFragment.arguments = args
        val transaction = supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, newFragment)
            addToBackStack(null)
        }

        transaction.commit()
    }
}