package com.example.eventssicredi.ui.eventfragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.eventssicredi.R
import com.example.eventssicredi.databinding.EventDetailFragmentBinding
import com.example.eventssicredi.model.Checkin
import com.example.eventssicredi.service.EventRepository
import com.example.eventssicredi.utils.Util
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.bottom_sheet_checkin.*
import kotlinx.android.synthetic.main.event_detail_fragment.*

class EventFragment : Fragment(R.layout.event_detail_fragment) {

    private lateinit var viewModel: EventViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private var binding: EventDetailFragmentBinding? = null

    private val args: EventFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventDetailFragmentBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sendService = EventRepository()
        viewModel = ViewModelProvider(
            this,
            EventViewModel.EventViewModelFactory(sendService))[EventViewModel::class.java]
        binding?.event?.let {
            viewModel.loadAddress(requireContext(), it!!.latitude, it.longitude)
        }

        viewModel.address.observe(viewLifecycleOwner, {
            binding?.viewModel = viewModel
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            if (it.equals(R.string.connection_success))
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            else
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.event.let {
            binding?.event = it
        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isHideable = false

        setViewListeners()
    }

    private fun setViewListeners() {
        send_button?.setOnClickListener {
            val userInfo = args.event?.id?.let { it1 ->
                Checkin(  eventId = it1.toInt(),
                    name = name_send.text.toString(),
                    email = email_send.text.toString())
            }

            if (userInfo != null) viewModel.sendCheckin(userInfo)
        }

        shareButton.setOnClickListener{
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.putExtra(Intent.EXTRA_TEXT, viewModel.address.value + binding?.event?.toString())
                } else {
                    this.putExtra(Intent.EXTRA_TEXT,binding?.event?.description)
                }
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }

        locationEvent.setOnClickListener {
            binding?.event?.let {
                val gmmIntentUri =
                    Uri.parse("geo:0,0?q=${it.latitude},${it.longitude}(Google+${it.title})")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }

        bottomSheet.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

    }

}