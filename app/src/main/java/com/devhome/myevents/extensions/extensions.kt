package com.devhome.myevents.extensions

import android.content.SharedPreferences

fun SharedPreferences.putAny(name: String, any: Any) {
    when (any) {
        is String -> edit().putString(name, any).apply()
        is Int -> edit().putInt(name, any).apply()
        is Boolean -> edit().putBoolean(name,any).apply()
    }
}

fun SharedPreferences.getBoolean(name: String, value:Boolean):Boolean {
    return getBoolean(name,value)
}