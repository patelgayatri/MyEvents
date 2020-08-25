package com.devhome.myevents.data.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable



@Entity
class Events :Serializable{
    @PrimaryKey(autoGenerate = true)
    var e_id: Int = 0

    @ColumnInfo(name = "event_name")
    var eventName: String? = null

    @ColumnInfo(name = "event_date")
    var eventDate: String? = null

    @ColumnInfo(name = "event_time")
    var eventTime: String? = null
}