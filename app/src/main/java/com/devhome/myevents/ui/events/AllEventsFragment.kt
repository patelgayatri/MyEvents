package com.devhome.myevents.ui.events

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devhome.myevents.R
import com.devhome.myevents.data.entity.Events
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_allevent.*

class AllEventsFragment : Fragment(), EventsAdapter.Listener {

    private val viewModel: AllEventsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar?.show()
        val root = inflater.inflate(R.layout.fragment_allevent, container, false)
        initialize(root)
        return root
    }

    private fun initialize(root: View?) {

        val recyclerView = root?.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = EventsAdapter(this)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        viewModel.allEvents.observe(viewLifecycleOwner, Observer { events ->
            events.let {
                if (it.size > 0) no_data_lin.visibility = View.GONE
                else no_data_lin.visibility = View.VISIBLE
                adapter.setEvents(it)
            }
        })
    }

    override fun onItemClick(event: Events, position: Int) {
        val bundle = bundleOf("event" to event, "pos" to position)
        findNavController().navigate(R.id.action_AllEventFragment_to_AddEventFragment, bundle)
    }

    override fun onLongitemClick(events: Events): Boolean {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setTitle(getString(R.string.str_dlt))
        dialogBuilder.setMessage(getString(R.string.str_dlt_que) + events.eventName)
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                viewModel.delete(events)
                dialog.cancel()


            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("AlertDialogExample")
        alert.show()
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_AllEventFragment_to_AddEventFragment)
        }
    }
}

