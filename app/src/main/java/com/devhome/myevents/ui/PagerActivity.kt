package com.devhome.myevents.ui

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.devhome.myevents.Prefs
import com.devhome.myevents.R
import com.devhome.myevents.extensions.putAny
import com.devhome.myevents.ui.pager.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_pager.*


class PagerActivity : AppCompatActivity(), View.OnClickListener {

    private var currentPage = 0
    private lateinit var indicators: Array<ImageView>
    private lateinit var viewPager: ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
        Prefs.putAny("first_time",false)

        setViewPager()
        intro_btn_next.setOnClickListener(this)
        intro_btn_skip.setOnClickListener(this)
    }

    private fun setViewPager() {
        indicators = arrayOf(
            findViewById<View>(R.id.intro_indicator_0) as ImageView,
            findViewById<View>(R.id.intro_indicator_1) as ImageView,
            findViewById<View>(R.id.intro_indicator_2) as ImageView
        )
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                updateIndicator(position)

            }
        })
    }

    private fun updateIndicator(position: Int) {
        for (i in 0 until indicators.size) {
            if (i == position) {
                indicators.get(i).setImageResource(R.drawable.indicator_selected)
            } else indicators.get(i).setImageResource(R.drawable.indicator_unselected)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.intro_btn_skip -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.intro_btn_next -> {
                when (currentPage) {
                    2 -> {
                        val intent = Intent(this, MainActivity::class.java)
                        val options = ActivityOptions.makeSceneTransitionAnimation(this)
                        startActivity(intent, options.toBundle())
                        finish()
                    }
                    else -> {
                        viewPager.currentItem = currentPage + 1
                    }
                }
            }
        }

    }

}

