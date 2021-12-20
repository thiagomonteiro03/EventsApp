package com.example.eventssicredi.ui.eventListFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventssicredi.R
import com.example.eventssicredi.databinding.EventItemBinding
import com.example.eventssicredi.databinding.EventListFragmentBinding
import com.example.eventssicredi.model.EventEntity

class EventListAdapter(
    private var events: List<EventEntity>,
    private val viewModel: EventListViewModel,
) : RecyclerView.Adapter<ViewHolder>() {

    var onItemClick: ((entity: EventEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setEvents(events: List<EventEntity>) {
        this.events = events
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eventEntity = events[holder.adapterPosition]
        holder.bind(eventEntity,viewModel)
        setOnClickListener(holder, eventEntity)
    }

    private fun setOnClickListener(
        holder: ViewHolder,
        eventEntity: EventEntity
    ) {
        holder.itemView.setOnClickListener {
        }
    }

}

class ViewHolder constructor(val binding: EventItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var masterPropertyValue: String? = null
    fun bind(item: EventEntity, viewModel: EventListViewModel) {
        binding.event = item
        binding.viewModel = viewModel
    }

    var isSelected = false

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = EventItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}