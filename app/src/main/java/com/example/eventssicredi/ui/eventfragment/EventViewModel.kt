package com.example.eventssicredi.ui.eventfragment

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.*
import com.example.eventssicredi.model.Checkin
import com.example.eventssicredi.service.EventRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val _address = MutableLiveData<String>()
    val address : LiveData<String>
        get() = _address

    fun sendCheckin(userInfo: Checkin){
        viewModelScope.launch {
            val response : Response<Checkin> = repository.sendCheckin(userInfo)
            response.isSuccessful
//                        if (it?.id != null && Util.validateEmailFormat(email_send.text.toString())) {
//                            Toast.makeText(requireContext(), R.string.check_in_sent_message, Toast.LENGTH_SHORT).show()
//                            bottomSheetBehavior.isHideable = true
//                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//                        } else {
//                            if (Util.validateEmailFormat(email_send.text.toString())){
//                                Toast.makeText(requireContext(), R.string.check_in_error_message, Toast.LENGTH_SHORT).show()}
//                            else Toast.makeText(requireContext(), R.string.check_email_error_message, Toast.LENGTH_SHORT).show()
//                        }
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