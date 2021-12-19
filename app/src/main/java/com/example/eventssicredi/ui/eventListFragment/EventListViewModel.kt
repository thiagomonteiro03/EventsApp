package com.example.eventssicredi.ui.eventListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eventssicredi.model.EventEntity
import com.example.eventssicredi.service.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventListViewModel(private val repository: EventRepository) : ViewModel() {

    private val _events = MutableLiveData<List<EventEntity>>()
    val events : LiveData<List<EventEntity>>
    get() = _events

    fun loadEvents() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = withContext(Dispatchers.Default) {
                repository.getApiData()
            }
            _events.value = response ?: listOf()
        }
    }

    class EventListViewModelFactory(
        private val repository: EventRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            TODO("Not yet implemented")
        }
    }

}
