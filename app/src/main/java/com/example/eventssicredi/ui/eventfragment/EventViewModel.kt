package com.example.eventssicredi.ui.eventfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.eventssicredi.model.Checkin
import com.example.eventssicredi.service.EventRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class EventViewModel(private val repository: EventRepository) : ViewModel() {

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

    class EventViewModelFactory(
        private val repository: EventRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EventViewModel(repository) as T
        }
    }

}