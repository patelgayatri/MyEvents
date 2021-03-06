package com.devhome.myevents.ui

import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.view.Menu
import android.view.MenuItem
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.devhome.myevents.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimation();
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }


    fun setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            val explode = Explode()
            explode.setDuration(800)
            explode.setInterpolator(DecelerateInterpolator())
            window.exitTransition = explode
            window.enterTransition = explode
        }
    }
}