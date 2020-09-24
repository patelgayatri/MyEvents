package com.devhome.myevents.extensions

import android.content.SharedPreferences
import java.util.concurrent.atomic.AtomicInteger

fun SharedPreferences.putAny(name: String, any: Any) {
    when (any) {
        is String -> edit().putString(name, any).apply()
        is Int -> edit().putInt(name, any).apply()
        is Boolean -> edit().putBoolean(name, any).apply()
    }
}

object NotificationID {
    private val c: AtomicInteger = AtomicInteger(0)
    val iD: Int
        get() = c.incrementAndGet()
}

fun getNotificationID(): Int {
    val c: AtomicInteger = AtomicInteger(0)
    val nID: Int = c.incrementAndGet()
    return nID
}