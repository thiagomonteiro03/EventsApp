package com.example.eventssicredi.service

import com.example.eventssicredi.model.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventRepository {

    suspend fun getApiData() : List<EventEntity>? {
        return withContext(Dispatchers.Default) {
            val retrofitClient = RetrofitUtils
                .getRetrofitInstance(RetrofitConstants.URL)

            val endpoint = retrofitClient.create(RetrofitInterface::class.java)
            return@withContext endpoint.getEvents()
        }
    }
}