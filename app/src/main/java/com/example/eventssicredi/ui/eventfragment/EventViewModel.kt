package com.example.eventssicredi.ui.eventfragment

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.*
import com.example.eventssicredi.R
import com.example.eventssicredi.model.Checkin
import com.example.eventssicredi.service.EventRepository
import com.example.eventssicredi.utils.Util
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val _address = MutableLiveData<String>()
    val address : LiveData<String>
        get() = _address

    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage : LiveData<Int>
        get() = _errorMessage


    fun sendCheckin(userInfo: Checkin){
        if (Util.validateEmailFormat(userInfo.email))
        viewModelScope.launch { handlingResponse(userInfo) }
        else _errorMessage.value = R.string.check_email_error_message
    }

    suspend fun handlingResponse(userInfo: Checkin){
        repository.sendCheckin(userInfo).let {
            when(it.raw().code){
                200 -> _errorMessage.value = R.string.connection_success
                400 -> _errorMessage.value = R.string.connection_error_400
                401 -> _errorMessage.value = R.string.connection_error_401
                403 -> _errorMessage.value = R.string.connection_error_403
                500 -> _errorMessage.value = R.string.connection_error_500
                503 -> _errorMessage.value = R.string.connection_error_503
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