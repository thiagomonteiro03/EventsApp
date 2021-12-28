package com.example.eventssicredi.ui.eventfragment

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.*
import com.example.eventssicredi.R
import com.example.eventssicredi.model.Checkin
import com.example.eventssicredi.service.EventRepository
import com.example.eventssicredi.service.RepositoryInterface
import com.example.eventssicredi.utils.Util
import kotlinx.coroutines.launch
import java.util.*

class EventViewModel(private val repository: RepositoryInterface) : ViewModel() {

    private val _address = MutableLiveData<String>()
    val address : LiveData<String>
        get() = _address

    private val _postMessage = MutableLiveData<Int>()
    val postMessage : LiveData<Int>
        get() = _postMessage


    fun sendCheckin(userInfo: Checkin){
        if (Util.validateEmailFormat(userInfo.email)) {
        viewModelScope.launch { handlingResponse(userInfo) }
        } else _postMessage.value = R.string.check_email_error_message
    }

    suspend fun handlingResponse(userInfo: Checkin){
            repository.sendCheckin(userInfo).let {
                if(it.isSuccessful) _postMessage.value = R.string.connection_success
                else {
                    when (it.raw().code) {
                        400 -> _postMessage.value = R.string.connection_error_400
                        401 -> _postMessage.value = R.string.connection_error_401
                        403 -> _postMessage.value = R.string.connection_error_403
                        500 -> _postMessage.value = R.string.connection_error_500
                        503 -> _postMessage.value = R.string.connection_error_503
                    }
                }
            }
    }

    fun loadAddress(context: Context, latitude : Double, longitude: Double){
        try {
            val geo = Geocoder(context, Locale.getDefault())
            val addresses : List<Address> = geo.getFromLocation(latitude, longitude, 1)
            if (addresses.isEmpty()) {
                _address.value = ""
            }
            else {
                if (addresses.isNotEmpty()) {
                    _address.value = addresses[0].getAddressLine(0)
                }
            }
        } catch (ex: Exception){
            Log.e("Geocoder error", ex.toString())
        }
    }

    class EventViewModelFactory(
        private val repository: EventRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EventViewModel(repository) as T
        }
    }

}