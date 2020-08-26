package com.devhome.myevents.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devhome.myevents.R
import com.devhome.myevents.data.entity.Events
import com.devhome.myevents.view.adapters.EventsAdapter
import com.devhome.myevents.viewmodel.AllEventsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllEventsFragment : Fragment(), EventsAdapter.Listener {

    companion object {
        fun newInstance() =
            AllEventsFragment()
    }

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

        var recyclerView = root?.findViewById<View>(R.id.recyclerview) as RecyclerView

        with(recyclerView) {
            val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(activity)
            this.layoutManager = linearLayoutManager
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
        }
        recyclerView.isNestedScrollingEnabled = true

        viewModel.getEvents()
        viewModel.mutableLiveData.observe(
            viewLifecycleOwner,
            Observer { productList ->

                productList?.let {
                    var productListAdapter = EventsAdapter(it.toTypedArray(), this)
                    recyclerView.adapter = productListAdapter
                }
            })
//        var tracker = SelectionTracker.Builder(
//            "my-selection-id",
//            recyclerView,
//            StableIdKeyProvider(recyclerView),
//            MyDetailsLookup(recyclerView),
//            StorageStrategy.createLongStorage())
//            .withOnItemActivatedListener(myItemActivatedListener)
//            .build()
    }

    override fun onItemClick(event: Events) {
        val bundle = bundleOf("event" to event)
        findNavController().navigate(R.id.action_AllEventFragment_to_AddEventFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.title = "My Events"

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_AllEventFragment_to_AddEventFragment)
        }
    }
}

