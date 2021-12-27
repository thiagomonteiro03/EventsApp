package com.example.eventssicredi.model

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.eventssicredi.R
import com.example.eventssicredi.utils.Util
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventEntity(     val date : Long,
                            val description: String,
                            val image: String,
                            val longitude : Double,
                            val latitude : Double,
                            val price : Double,
                            val title : String,
                            val id : String) : Parcelable {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun toString(): String {
        return " ${Util.getDateFormat(date, "dd/MM/yyyy HH:mm")}\n $title\n" +
                " ${Util.getRealCurrency(price)}\n $description"
    }
}
