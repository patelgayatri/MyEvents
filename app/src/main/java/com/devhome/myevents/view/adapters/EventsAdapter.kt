package com.devhome.myevents.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devhome.myevents.R
import com.devhome.myevents.data.entity.Events
import kotlinx.android.synthetic.main.raw_dashboard.view.*

class EventsAdapter(private val dataList: Array<Events>, private val listener: Listener) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClick(events: Events)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position], listener, position)
    }

    override fun getItemCount(): Int = dataList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.raw_dashboard, parent, false)

        return ViewHolder(view)
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
            itemView.setOnClickListener { listener.onItemClick(events) }


        }
    }
}