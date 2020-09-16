package com.devhome.myevents.ui.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devhome.myevents.R
import com.devhome.myevents.data.entity.Events
import com.devhome.myevents.utils.displayDateFormat
import kotlinx.android.synthetic.main.raw_dashboard.view.*
import java.text.SimpleDateFormat
import java.util.*

class EventsAdapter(private val listener: Listener) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

     private var eventList= emptyList<Events>()

    interface Listener {
        fun onItemClick(events: Events, position: Int)
        fun onLongitemClick(events: Events):Boolean
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventList[position], listener, position)
    }

    override fun getItemCount(): Int = eventList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.raw_dashboard, parent, false)

        return ViewHolder(view)
    }
    internal fun setEvents(events:  List<Events>) {
        this.eventList = events
        notifyDataSetChanged()
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            events: Events,
            listener: Listener,
            position: Int
        ) {

            itemView.name_txt.text = events.eventName
            itemView.date_txt.text = events.eventDate
            itemView.time_txt.text = events.eventTime
            setCounterData(events)
            itemView.setOnClickListener { listener.onItemClick(events,position) }
            itemView.setOnLongClickListener {
                listener.onLongitemClick(events)
            }


        }

        private fun setCounterData(eventData: Events) {
            try {
                val currentTime = Calendar.getInstance().time
                val endDateDay = eventData.eventDate + " " + eventData.eventTime
                val format1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
                val endDate = format1.parse(endDateDay)
                val difference = (endDate.time / (24 * 60 * 60 * 1000)
                        - (currentTime.time / (24 * 60 * 60 * 1000)))
                itemView.day_txt.text = "$difference"

                val d = displayDateFormat.format(endDate)
                itemView.date_txt.text = d

                val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val timeStr=timeFormat.parse(eventData.eventTime)
                val timeNewFormat = SimpleDateFormat("hh:mm a", Locale.US)
                val timeNewStr=timeNewFormat.format(timeStr)
                itemView.time_txt.text=timeNewStr



            } catch (e: Exception) {
                //java.text.ParseException: Unparseable date: Geting error
                println("Excep$e")
            }
        }


    }
}
