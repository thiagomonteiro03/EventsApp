package com.example.eventssicredi.service

import com.example.eventssicredi.model.Checkin
import com.example.eventssicredi.model.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class EventRepository : RepositoryInterface{

    override suspend fun getApiData(): Response<List<EventEntity?>> {
         return withContext(Dispatchers.Default) {
            val retrofitClient = RetrofitUtils
                .getRetrofitInstance(RetrofitConstants.URL)
            val endpoint = retrofitClient.create(RetrofitInterface::class.java)
            endpoint.getEvents()
        }
    }

    override suspend fun sendCheckin(userSend: Checkin): Response<Checkin> {
        return withContext(Dispatchers.Default) {
            val retrofit = RetrofitInitializer().buildService(RetrofitInterface::class.java)
            retrofit.addCheckin(userSend)
        }
    }

}