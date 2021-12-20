package com.example.eventssicredi.model


data class EventEntity(     val date : Long,
                            val description: String,
                            val image: String,
                            val longitude : Double,
                            val latitude : Double,
                            val price : Double,
                            val title : String,
                            val id : String)
