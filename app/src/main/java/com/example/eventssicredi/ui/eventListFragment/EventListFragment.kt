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
import java.util.*

class EventListFragment : Fragment() {

    private var adapter: EventListAdapter? = null
    private var binding: EventListFragmentBinding? = null
    private var eventList = listOf(
        EventEntity(0, "baby eu só consigo dormir com livros", "3", 32.0, 32.0, 32.0, "3", "3"),
        EventEntity(0, "baby eu só consigo dormir com livros 2", "3", 32.0, 32.0, 32.0, "PODE FALA OQ SE TA AFIM", "3")
    )

    companion object {
        fun newInstance() = EventListFragment()
    }

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
        viewModel = ViewModelProvider(this).get(EventListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding?.recyclerBooks?.let {
            this.adapter = EventListAdapter(eventList, viewModel)
            it.adapter = this.adapter
        } ?: throw AssertionError()
    }

}