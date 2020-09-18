package com.devhome.myevents

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

lateinit var Prefs: SharedPreferences

class App:Application(){

    override fun onCreate() {
        super.onCreate()
        Prefs= PreferenceManager.getDefaultSharedPreferences(this)
    }
}