package com.devhome.myevents.ui.events

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.devhome.myevents.R
import com.devhome.myevents.data.entity.Events
import com.devhome.myevents.utils.displayDateFormat
import com.devhome.myevents.utils.saveDateFormat
import kotlinx.android.synthetic.main.fragment_add_event.*
import kotlinx.android.synthetic.main.raw_dashboard.view.*
import java.text.SimpleDateFormat
import java.util.*

class EventsAdapter(private val listener: Listener) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    private var eventList = emptyList<Events>()
    var colorList = arrayOf("#4CAF50", "#FFC107", "#673AB7", "#E91E63")

    interface Listener {
        fun onItemClick(events: Events, position: Int)
        fun onLongitemClick(events: Events): Boolean
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventList[position], colorList, listener, position)
    }

    override fun getItemCount(): Int = eventList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.raw_dashboard, parent, false)

        return ViewHolder(view)
    }

    internal fun setEvents(events: List<Events>) {
        this.eventList = events
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            events: Events,
            colorList: Array<String>,
            listener: Listener,
            position: Int
        ) {
            var card = itemView.findViewById<CardView>(R.id.card)
            itemView.name_txt.text = events.eventName
            itemView.date_txt.text = events.eventDate
            itemView.time_txt.text = events.eventTime
            card.setCardBackgroundColor(Color.parseColor(colorList[position % 3]))

            setCounterData(events)
            itemView.setOnClickListener { listener.onItemClick(events, position) }
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
                val sameDate = saveDateFormat.format(currentTime)

                val difference = (endDate.time / (24 * 60 * 60 * 1000)
                        - (currentTime.time / (24 * 60 * 60 * 1000)))
                if (sameDate == eventData.eventDate) {
                    itemView.day_txt.text = itemView.context.getString(R.string.today)
                    itemView.daysLable.visibility = View.GONE
                } else
                    itemView.day_txt.text = "$difference"

                val d = displayDateFormat.format(endDate)
                itemView.date_txt.text = d

                val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val timeStr = timeFormat.parse(eventData.eventTime)
                val timeNewFormat = SimpleDateFormat("hh:mm a", Locale.US)
                val timeNewStr = timeNewFormat.format(timeStr)
                itemView.time_txt.text = timeNewStr


            } catch (e: Exception) {
                //java.text.ParseException: Unparseable date: Geting error
                println("Excep$e")
            }
        }


    }
}
