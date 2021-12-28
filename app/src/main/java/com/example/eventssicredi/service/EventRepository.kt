package com.example.eventssicredi.service

import android.util.Log
import com.example.eventssicredi.model.Checkin
import com.example.eventssicredi.model.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class EventRepository : RepositoryInterface{

    override suspend fun getApiData(): Response<List<EventEntity?>> {
        try {
            return withContext(Dispatchers.Default) {
                val retrofitClient = RetrofitUtils
                    .getRetrofitInstance(RetrofitConstants.URL)
                val endpoint = retrofitClient.create(RetrofitInterface::class.java)
                endpoint.getEvents()
            }
        } catch (ex: ExceptionInInitializerError){
            Log.e("getApiData error", ex.toString())
        }
        return Response.success(listOf())
    }

    override suspend fun sendCheckin(userSend: Checkin): Response<Checkin> {
        return withContext(Dispatchers.Default) {
            val retrofit = RetrofitInitializer().buildService(RetrofitInterface::class.java)
            retrofit.addCheckin(userSend)
        }
    }

}