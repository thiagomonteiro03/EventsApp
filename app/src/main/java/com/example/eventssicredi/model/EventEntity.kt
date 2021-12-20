package com.example.eventssicredi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventEntity(     val date : Long,
                            val description: String,
                            val image: String,
                            val longitude : Double,
                            val latitude : Double,
                            val price : Double,
                            val title : String,
                            val id : String) : Parcelable
