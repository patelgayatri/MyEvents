package com.devhome.myevents.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.devhome.myevents.data.AppDatabase
import com.devhome.myevents.data.entity.Events
import com.devhome.myevents.data.repo.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllEventsViewModel(application: Application):AndroidViewModel(application){

    private val repository:EventRepository
    val allEvents:LiveData<List<Events>>

    init {
        val eventDao=AppDatabase.getInstance(application.applicationContext).eventsDao()
        repository= EventRepository(eventDao)
        allEvents=repository.eventList
    }
    fun insert(events: Events)=viewModelScope.launch(Dispatchers.IO) {
        repository.insertEvent(events)
    }
    fun update(events: Events)=viewModelScope.launch(Dispatchers.IO) {
        repository.updateEvent(events)
    }

}