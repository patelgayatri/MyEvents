package com.devhome.myevents.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devhome.myevents.Prefs
import com.devhome.myevents.R
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Splash : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.primaryColor)

        val firstTime = Prefs.getBoolean("first_time", true)
        val animation: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.zoom_in
        )
        splashText.startAnimation(animation)

        val intent: Intent = if (firstTime)
            Intent(this, PagerActivity::class.java)
        else
            Intent(this, MainActivity::class.java)

        GlobalScope.launch {
            delay(3000L)
            startActivity(intent)
            finish()
        }

    }
}