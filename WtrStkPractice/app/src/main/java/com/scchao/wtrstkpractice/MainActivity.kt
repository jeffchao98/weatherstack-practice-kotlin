package com.scchao.wtrstkpractice

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, listFragment)
            .commit()
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

    //Override the dispatchTouchEvent method in Activity for close the softKeyboard
    //when the EditText view under focused state and the non-EditText view been clicked
    //Reference: https://stackoverflow.com/a/32149756
    //When the conditions meet, dismiss the keyboard and clear the focus state for EditText
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var view = currentFocus
        if (view != null
            && (ev?.action == MotionEvent.ACTION_UP || ev?.action == MotionEvent.ACTION_MOVE)
            && view is EditText
            && !view.javaClass.name.startsWith("android.webkit")
        ) {
            var record = IntArray(2)
            view.getLocationOnScreen(record)
            val x = ev?.getRawX() + view.left - record[0]
            val y = ev?.getRawY() + view.top - record[1]
            if (x < view.left || x > view.right || y < view.top || y > view.bottom) {
                hideKeyboard(this)
                view.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun hideKeyboard(activity: Activity) {
        if (activity != null && activity.window != null && activity.window.decorView != null) {
            var imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }

}