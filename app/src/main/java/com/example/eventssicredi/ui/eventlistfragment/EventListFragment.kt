package com.example.eventssicredi.ui.eventlistfragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eventssicredi.databinding.EventListFragmentBinding
import com.example.eventssicredi.model.EventEntity
import com.example.eventssicredi.service.EventRepository
import com.example.eventssicredi.utils.navigateWithAnimations
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.event_list_fragment.*

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

        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           loadItems()
        } else {
            viewModel.loadEvents()
        }

        viewModel.events.observe(viewLifecycleOwner, {
            val eventListAdapter = EventListAdapter(it, viewModel).apply {
                onItemClick = { event ->
                    val directions = EventListFragmentDirections.actionEventListFragmentToEventFragment(event)
                    findNavController().navigateWithAnimations(directions)
                }
            }

            with(recyclerBooks) {
                setHasFixedSize(true)
                adapter = eventListAdapter
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding!!.progressBarHome.visibility = View.VISIBLE
            } else {
                binding!!.progressBarHome.visibility = View.GONE
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding?.recyclerBooks?.let {
            this.adapter = EventListAdapter(eventList, viewModel)
            it.adapter = this.adapter
        } ?: throw AssertionError()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun loadItems(){
        if(isInternetConnected(requireContext())){
            viewModel.loadEvents()
        } else {
            binding?.clWifiOff?.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun isInternetConnected(getApplicationContext: Context): Boolean {
        var status = false
        val cm =
            getApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            if (cm.activeNetwork != null && cm.getNetworkCapabilities(cm.activeNetwork) != null) {
                // connected to the internet
                status = true
            }
        }
        return status
    }

}