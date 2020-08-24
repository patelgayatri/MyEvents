package com.devhome.myevents.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Events(
    @ColumnInfo(name = "event_name") var eventName: String,
    @ColumnInfo(name = "event_date") var eventDate: String,
    @ColumnInfo(name = "event_time") var eventTime: String
) {
    @PrimaryKey(autoGenerate = true)
    var e_id: Int=0


}