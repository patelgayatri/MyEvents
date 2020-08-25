package com.devhome.myevents.data.dao

import androidx.room.*
import com.devhome.myevents.data.entity.Events

@Dao
interface EventDao {

    @Query("SELECT * FROM EVENTS")
    fun getAllEvents(): List<Events>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(events: Events)

    @Update
    fun updateEvent(events: Events)

    @Delete
    fun deleteEvent(events: Events)
}