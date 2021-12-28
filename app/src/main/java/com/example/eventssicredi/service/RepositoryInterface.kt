package com.example.eventssicredi.service

import com.example.eventssicredi.model.Checkin
import com.example.eventssicredi.model.EventEntity
import retrofit2.Response

interface RepositoryInterface {

    suspend fun getApiData(): Response<List<EventEntity?>> {
        return Response.success(listOf())
    }

    suspend fun sendCheckin(userSend: Checkin): Response<Checkin> {
        return Response.success(Checkin(1, "name", "email"))
    }

}