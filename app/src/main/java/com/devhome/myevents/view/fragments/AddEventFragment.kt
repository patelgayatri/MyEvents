package com.devhome.myevents.view.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.devhome.myevents.R
import com.devhome.myevents.data.AppDatabase
import com.devhome.myevents.data.entity.Events
import kotlinx.android.synthetic.main.fragment_add_event.*
import java.text.SimpleDateFormat
import java.util.*


class AddEventFragment : Fragment() {

    lateinit var cal: Calendar
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    lateinit var eventData: Events
     var countDownTimer: CountDownTimer?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeData()

        eventDate.setOnClickListener {
            datePickerDialogue()
        }

        eventTime.setOnClickListener {
            timePickerDialogue()
        }
        view.findViewById<Button>(R.id.add_btn).setOnClickListener {

            insertData()
        }
    }


    private fun timePickerDialogue() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val myFormat = "kk:mm"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            val formated_time = sdf.format(cal.getTime())
            eventTime.setText(formated_time + ":00")
            //eventTime.setText("$hour:$minute:00")
        }

        TimePickerDialog(
            activity, timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun datePickerDialogue() {

        val dpd = DatePickerDialog(
            this.requireActivity(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val dateFormat =
                    SimpleDateFormat("dd:MM:yyyy")
                cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0)
                val dateString = dateFormat.format(cal.time)
                eventDate.setText(dateString)
            },
            year,
            month,
            day
        )
        dpd.show()

    }

    private fun initializeData() {
        when {
            arguments != null -> {
                eventData = arguments?.getSerializable("event") as Events
                eventName.setText(eventData.eventName)
                eventDate.setText(eventData.eventDate)
                eventTime.setText(eventData.eventTime)
                setCounter(eventData)
            }
        }
        cal = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DAY_OF_MONTH)
    }

    private fun insertData() {
        if (isValidate()) {
            val local = activity?.application?.let { AppDatabase.getInstance(it).eventsDao() }
            var event = Events()
            event.eventName = eventName.text.toString()
            event.eventDate = eventDate.text.toString()
            event.eventTime = eventTime.text.toString()
            when {
                arguments != null -> {
                    event.e_id = eventData.e_id
                    local?.updateEvent(event)
                }
                else -> {
                    local?.insertEvent(event)
                }
            }
            countDownTimer?.cancel()
            activity?.onBackPressed()
            //findNavController().navigate(R.id.action_AddEventFragment_to_AllEventFragment)
        }
    }

    private fun isValidate(): Boolean {
        when {
            eventName.text.toString().isEmpty() -> {
                Toast.makeText(activity, "Enter Event Name", Toast.LENGTH_SHORT).show()
                return false
            }
            eventDate.text.toString().isEmpty() -> {
                Toast.makeText(activity, "Enter Date", Toast.LENGTH_SHORT).show()
                return false
            }
            eventTime.text.toString().isEmpty() -> {
                Toast.makeText(activity, "Enter Time", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return true
        }
    }

    private fun setCounter(eventData: Events) {
        val currentTime = Calendar.getInstance().time
        val endDateDay = eventData.eventDate + " " + eventData.eventTime
        val format1 = SimpleDateFormat("dd:MM:yyyy hh:mm:ss", Locale.getDefault())
        val endDate = format1.parse(endDateDay)

        //milliseconds
        var different = endDate.time - currentTime.time
        countDownTimer = object : CountDownTimer(different, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = diff / daysInMilli
                diff %= daysInMilli

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                headerDetail.text =
                    "$elapsedDays days $elapsedHours hs $elapsedMinutes min $elapsedSeconds sec"
            }

            override fun onFinish() {
                headerDetail.text = "Wait is over!"
            }
        }.start()
    }
}