package com.example.eventssicredi.ui.eventListFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eventssicredi.databinding.EventItemBinding
import com.example.eventssicredi.model.EventEntity

class EventListAdapter(
    private var events: List<EventEntity>,
    private val viewModel: EventListViewModel,
) : RecyclerView.Adapter<ViewHolder>() {

    var onItemClick: ((entity: EventEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eventEntity = events[holder.adapterPosition]
        holder.bind(eventEntity,viewModel)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(eventEntity)
        }
    }
}

class ViewHolder constructor(val binding: EventItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: EventEntity, viewModel: EventListViewModel) {
        binding.event = item
        binding.viewModel = viewModel
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = EventItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}