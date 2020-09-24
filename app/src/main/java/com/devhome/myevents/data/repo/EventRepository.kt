package com.devhome.myevents.data.repo

import androidx.lifecycle.LiveData
import com.devhome.myevents.data.dao.EventDao
import com.devhome.myevents.data.entity.Events

class EventRepository(private val eventDao: EventDao) {
    val eventList: LiveData<List<Events>> = eventDao.getAllEvents()

    fun insertEvent(events: Events) {
        return eventDao.insertEvent(events)
    }

    fun updateEvent(events: Events) {
        eventDao.updateEvent(events)
    }

    fun deleteEvent(events: Events) {
        eventDao.deleteEvent(events)
    }
}