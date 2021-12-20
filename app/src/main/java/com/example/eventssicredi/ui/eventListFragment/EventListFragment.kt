package com.example.eventssicredi.ui.eventListFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.eventssicredi.R
import com.example.eventssicredi.databinding.EventListFragmentBinding
import com.example.eventssicredi.model.EventEntity
import com.example.eventssicredi.service.EventRepository
import com.example.eventssicredi.ui.eventFragment.EventViewModel
import okhttp3.internal.notifyAll
import java.util.*
import kotlin.collections.ArrayList

class EventListFragment : Fragment() {

    private var adapter: EventListAdapter? = null
    private var binding: EventListFragmentBinding? = null
    private var eventList: ArrayList<EventEntity> = arrayListOf()

    private lateinit var viewModel: EventListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventListFragmentBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            EventListViewModel.EventListViewModelFactory(EventRepository()))[EventListViewModel::class.java]
        viewModel.loadEvents()

        viewModel.events.observe(viewLifecycleOwner, {
            adapter?.setEvents(it)
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding!!.progressBarHome.visibility = View.VISIBLE
            } else {
                binding!!.progressBarHome.visibility = View.GONE
            }
        })
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding?.recyclerBooks?.let {
            this.adapter = EventListAdapter(eventList, viewModel)
            it.adapter = this.adapter
        } ?: throw AssertionError()
    }

}