package com.devhome.myevents.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.devhome.myevents.data.entity.Events

@Dao
interface EventDao {

    @Query("SELECT * from events where event_date >= date() order by event_date")
    fun getAllEvents(): LiveData<List<Events>>

    @Query("SELECT * from events where event_date < date()")
    fun getPastEvents(): List<Events>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(events: Events)

    @Update
    fun updateEvent(events: Events)

    @Delete
    fun deleteEvent(events: Events)

}