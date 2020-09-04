package com.devhome.myevents.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devhome.myevents.R
import com.devhome.myevents.data.entity.Events
import com.devhome.myevents.view.adapters.EventsAdapter
import com.devhome.myevents.viewmodel.AllEventsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllEventsFragment : Fragment(), EventsAdapter.Listener {

    private val viewModel: AllEventsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                adapter.setEvents(it)
            }
        })
    }

    override fun onItemClick(event: Events, position: Int) {
        val bundle = bundleOf("event" to event,"pos" to position)
        findNavController().navigate(R.id.action_AllEventFragment_to_AddEventFragment, bundle)
    }

    override fun onLongitemClick(events: Events): Boolean {
//        activity?.let {
//            AlertDialogBu(it)
//                .setTitle(resources.getString(R.string.title))
//                .setMessage(resources.getString(R.string.supporting_text))
//                .setNeutralButton(resources.getString(R.string.yes)) { dialog, which ->
//                    // Respond to neutral button press
//                }
//                .setPositiveButton(resources.getString(R.string.no)) { dialog, which ->
//                    // Respond to positive button press
//                }
//                .show()
//        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_AllEventFragment_to_AddEventFragment)
        }
    }
}

