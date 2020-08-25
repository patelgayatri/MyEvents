package com.devhome.myevents.view.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.devhome.myevents.R
import kotlinx.android.synthetic.main.activity_splash.*


class Splash : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animation: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.zoom_in
        )
        splashText.startAnimation(animation)
        Handler().postDelayed({
            var intent=Intent(this, MainActivity::class.java)

            startActivity(intent)

            finish()
        }, SPLASH_TIME_OUT)
    }
}