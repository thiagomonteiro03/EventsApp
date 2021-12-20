package com.example.eventssicredi.service

import com.example.eventssicredi.model.EventEntity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitInterface {

    @GET(RetrofitConstants.URL)
    suspend fun getEvents() : Response<List<EventEntity?>>

}