package com.devhome.myevents.view.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devhome.myevents.R
import com.devhome.myevents.data.AppDatabase
import com.devhome.myevents.data.entity.Events
import kotlinx.android.synthetic.main.fragment_add_event.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class AddEventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Add Event"
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        eventDate.setOnClickListener {
            val dpd = DatePickerDialog(
                this.requireActivity(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    eventDate.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year)

                },
                year,
                month,
                day
            )
            dpd.show()
        }
        eventTime.setOnClickListener {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                eventTime.setText("$hour : $minute")
            }

            TimePickerDialog(activity, timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
        view.findViewById<Button>(R.id.add_btn).setOnClickListener {
            val local = activity?.application?.let { AppDatabase.getInstance(it).eventsDao() }
            var events: Events =
                Events(
                    eventName.text.toString(),
                    eventDate.text.toString(),
                    eventTime.text.toString()
                )
            var a = local?.insertEvent(events)
            findNavController().navigate(R.id.action_AddEventFragment_to_AllEventFragment)
        }
    }
}