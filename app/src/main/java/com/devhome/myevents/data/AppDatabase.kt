package com.devhome.myevents.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devhome.myevents.data.dao.EventDao
import com.devhome.myevents.data.entity.Events

@Database(entities = [Events::class],version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun eventsDao(): EventDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also {
                    INSTANCE = it
                }
            }
        private fun buildDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,"app.db").allowMainThreadQueries()
                .build()
    }
}
