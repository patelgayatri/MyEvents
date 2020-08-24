package com.devhome.myevents.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devhome.myevents.data.AppDatabase
import com.devhome.myevents.data.entity.Events
import org.w3c.dom.Entity

class AllEventsViewModel(application: Application) : AndroidViewModel(application) {

    var mutableLiveData: MutableLiveData<List<Events>> = MutableLiveData()
    private val local = AppDatabase.getInstance(application.applicationContext).eventsDao()

    fun getEvents() {
        mutableLiveData.value = local.getAllEvents()
    }
}