package com.example.eventssicredi.ui.eventlistfragment

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.eventssicredi.R
import com.example.eventssicredi.model.EventEntity
import com.example.eventssicredi.service.EventRepository
import kotlinx.coroutines.launch

class EventListViewModel(private val repository: EventRepository) : ViewModel() {

    private val _events = MutableLiveData<List<EventEntity>>()
    val events : LiveData<List<EventEntity>>
    get() = _events

    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage : LiveData<Int>
        get() = _errorMessage

    val loading = MutableLiveData<Boolean>()

    fun loadEvents() {
        loading.value = true
        viewModelScope.launch {
            handlingEvents(repository)
        }
    }

    suspend fun handlingEvents(repository: EventRepository){
        repository.getApiData().let {
            var eventListApi: List<EventEntity>? = null
            if (it.isSuccessful){
                eventListApi = it.body() as List<EventEntity>?
            } else{
                when(it.raw().code){
                    400 -> _errorMessage.value = R.string.connection_error_400
                    401 -> _errorMessage.value = R.string.connection_error_401
                    403 -> _errorMessage.value = R.string.connection_error_403
                    500 -> _errorMessage.value = R.string.connection_error_500
                    503 -> _errorMessage.value = R.string.connection_error_503
                }
            }

            if(!eventListApi.isNullOrEmpty()) {
                _events.value = eventListApi!!
            }

            loading.value = false
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("listImage")
        fun loadImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl).apply(RequestOptions().centerInside())
                .into(view)
        }
    }

    class EventListViewModelFactory(
        private val repository: EventRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EventListViewModel(repository) as T
        }
    }

}
