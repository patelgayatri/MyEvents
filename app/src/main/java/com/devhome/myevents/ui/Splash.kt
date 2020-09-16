package com.devhome.myevents.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.devhome.myevents.R
import kotlinx.android.synthetic.main.activity_splash.*


class Splash : AppCompatActivity() {

    private val timeOut: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animation: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.zoom_in
        )
        splashText.startAnimation(animation)


        Handler().postDelayed({

            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, timeOut)
    }
}