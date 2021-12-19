package com.example.eventssicredi.service

import com.example.eventssicredi.model.EventEntity
import retrofit2.http.GET

interface RetrofitInterface {

    @GET(RetrofitConstants.URL)
    fun getEvents() : List<EventEntity>

}