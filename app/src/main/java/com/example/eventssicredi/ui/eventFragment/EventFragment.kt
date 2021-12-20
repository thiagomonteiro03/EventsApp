package com.example.eventssicredi.ui.eventFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.eventssicredi.R

class EventFragment : Fragment(R.layout.event_detail_fragment) {

    private lateinit var viewModel: EventViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        // TODO: Use the ViewModel
    }

}