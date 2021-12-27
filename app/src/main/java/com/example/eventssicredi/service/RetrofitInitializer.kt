package com.example.eventssicredi.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl(RetrofitConstants.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun<Checkin> buildService(service: Class<Checkin>): Checkin{
        return retrofit.create(service)
    }
}