package com.devhome.myevents.ui.eventDetail

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devhome.myevents.R
import com.devhome.myevents.data.entity.Events
import com.devhome.myevents.ui.events.AllEventsViewModel
import com.devhome.myevents.utils.saveDateFormat
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_add_event.*
import java.text.SimpleDateFormat
import java.util.*


class AddEventFragment : Fragment() {

    private lateinit var cal: Calendar
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private lateinit var eventData: Events
    private var countDownTimer: CountDownTimer? = null
    private var TAG = "AddEventFragment"
    private val viewModel: AllEventsViewModel by viewModels()
    lateinit var textInputLayout: TextInputLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()
        val window: Window = activity?.window!!
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.navigationBarColor = ContextCompat.getColor(window.context, R.color.color2)
        window.statusBarColor = ContextCompat.getColor(window.context, R.color.color2)

        return inflater.inflate(R.layout.fragment_add_event, container, false)
    }


    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
        countDownTimer?.cancel()
    }

    override fun onResume() {
        super.onResume()
        initializeData()
        clickEvent()
    }

    private fun clickEvent() {
        eventDate.setOnClickListener {
            datePickerDialogue()
        }

        eventTime.setOnClickListener {
            timePickerDialogue()
        }
        add_btn.setOnClickListener {
            //insertData()
            createNotificationChannel(NotificationCompat.PRIORITY_DEFAULT, true)
        }
    }

    private fun timePickerDialogue() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val myFormat = "kk:mm"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            val formatedTime = sdf.format(cal.time)
            eventTime.setText("$formatedTime:00")
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
            { view, year, monthOfYear, dayOfMonth ->
                cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0)
                val dateString = saveDateFormat.format(cal.time)
                eventDate.setText(dateString)
            },
            year,
            month,
            day
        )
        dpd.datePicker.minDate = System.currentTimeMillis() - 1000
        dpd.show()

    }

    private fun initializeData() {
        textInputLayout = view?.findViewById(R.id.eventDaysLable) as TextInputLayout

        when {
            arguments != null -> {
                viewLin.visibility = View.VISIBLE
                eventData = arguments?.getSerializable("event") as Events
                val pos = arguments?.getInt("pos")
                eventName.setText(eventData.eventName)
                eventDate.setText(eventData.eventDate)
                eventTime.setText(eventData.eventTime)

                viewModel.allEvents.observe(viewLifecycleOwner, Observer {
                    setCounter(it[pos!!])
                })
            }
            else -> {
                eventDays.setText(getString(R.string.add_event))
            }

        }
        cal = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DAY_OF_MONTH)

    }

    private fun insertData() {
        if (isValidate()) {
            val event = Events()
            event.eventName = eventName.text.toString()
            event.eventDate = eventDate.text.toString()
            event.eventTime = eventTime.text.toString()
            when {
                arguments != null -> {
                    event.eId = eventData.eId
                    viewModel.update(event)
                }
                else -> {
                    viewModel.insert(event)
                }
            }
            countDownTimer?.cancel()
            activity?.onBackPressed()
            //findNavController().navigate(R.id.action_AddEventFragment_to_AllEventFragment)
        }
    }

    fun createNotificationChannel(
        importance: Int,
        showBadge: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 2
            val channelId = "${requireActivity().packageName}-Event"
            val channel = NotificationChannel(channelId, "event", importance)
            channel.description = "description"
            channel.setShowBadge(showBadge)

            // 3
            val notificationManager =
                requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

            val builder = NotificationCompat.Builder(requireActivity(), channelId)
                .setSmallIcon(R.drawable.icon_event)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(requireActivity())) {
                // notificationId is a unique int for each notification that you must define
                notify(10, builder.build())
            }
        }
    }

    private fun isValidate(): Boolean {
        return when {
            eventName.text.toString().isEmpty() -> {
                Toast.makeText(activity, "Enter Event Name", Toast.LENGTH_SHORT).show()
                false
            }
            eventDate.text.toString().isEmpty() -> {
                Toast.makeText(activity, "Enter Date", Toast.LENGTH_SHORT).show()
                false
            }
            eventTime.text.toString().isEmpty() -> {
                Toast.makeText(activity, "Enter Time", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }


    private fun setCounter(eventData: Events) {
        val currentTime = Calendar.getInstance().time
        val endDateDay = eventData.eventDate + " " + eventData.eventTime
        val format1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
        val endDate = format1.parse(endDateDay)
        val sameDate = saveDateFormat.format(currentTime)


        //milliseconds
        val different = endDate.time - currentTime.time
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
                when {
                    sameDate == eventData.eventDate -> {
                        eventDays.setText(getString(R.string.today))
                    }
                    elapsedDays == 0L -> {
                        eventDays.setText(getString(R.string.tomorrow))
                    }
                    else -> {
                        eventDays.setText(elapsedDays.toString())
                        eventDaysLable.hint = "Days"
                    }
                }
                horstText.setText("$elapsedHours")
                eventMins.setText("$elapsedMinutes")
                eventsecs.setText("$elapsedSeconds")
            }

            override fun onFinish() {
                viewLin.visibility = View.GONE
                eventDays.setText(getString(R.string.done))
            }
        }.start()
    }

}