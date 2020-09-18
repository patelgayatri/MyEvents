package com.devhome.myevents.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.devhome.myevents.R
import com.devhome.myevents.ui.pager.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_pager.*


class PagerActivity : AppCompatActivity() {
    private var currentPage = 0
    private lateinit var indicators: Array<ImageView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setAnimation()
        setContentView(R.layout.activity_pager)
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white));

        indicators = arrayOf(
            findViewById<View>(R.id.intro_indicator_0) as ImageView,
            findViewById<View>(R.id.intro_indicator_1) as ImageView,
            findViewById<View>(R.id.intro_indicator_2) as ImageView
        )
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                updateIndicator(position)

            }
        })
        intro_btn_next.setOnClickListener {
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
        intro_btn_skip.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun setAnimation(activityPager: View) {
        if (Build.VERSION.SDK_INT > 20) {
//            var scene: Scene
//            var fadeTransition: Transition = TransitionInflater.from(this).inflateTransition(R.transition.fade)
//            TransitionManager.go(activityPager, fadeTransition)
//            val rootView: ViewGroup = findViewById(R.id.mainLayout)
//            val fade: Fade = Fade(Fade.IN)
//            TransitionManager.beginDelayedTransition(rootView, mFade)
//            rootView.addView(labelText)
        }
    }

    private fun updateIndicator(position: Int) {
        for (i in 0 until indicators.size) {
            if (i == position) {
                indicators.get(i).setImageResource(R.drawable.indicator_selected)
            } else indicators.get(i).setImageResource(R.drawable.indicator_unselected)
        }
    }
}
