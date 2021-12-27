package com.example.eventssicredi.service

import com.example.eventssicredi.model.Checkin
import com.example.eventssicredi.model.EventEntity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitInterface {

    @GET(RetrofitConstants.GET_URL)
    suspend fun getEvents() : Response<List<EventEntity?>>

    @Headers("Content-Type: application/json")
    @POST(RetrofitConstants.POST_URL)
    suspend fun addCheckin(@Body userSend : Checkin) : Response<Checkin>

}