package com.example.eventssicredi.Utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

open class Util {

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        @JvmStatic
        fun getDateFormat(date : LocalDateTime?, pattern: String): String {
            val formatter = DateTimeFormatter.ofPattern(pattern)
            return date?.format(formatter)?: ""
        }

        @JvmStatic
        fun getTimeFormat(time : LocalTime?): String {
            return time?.toString() ?: ""
        }

        @JvmStatic
        fun replaceInitialZeros(s: String?): String{
            return s?.trimStart('0') ?: ""
        }

        @JvmStatic
        fun replaceWithoutCharacters(s: String?): String{
            return s?.replace("[,.]".toRegex(), "") ?: "0"
        }

        @JvmStatic
        fun getRealCurrency(valor: Double): String {
            val nf: NumberFormat = NumberFormat.getCurrencyInstance()
            return nf.format(valor)
        }
    }
}