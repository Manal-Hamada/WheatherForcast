package com.example.wheatherforcast.utils

import java.text.SimpleDateFormat
import java.util.*

class DayFormatter {

    companion object{
        fun getDay(dt: Int): String {
            val cityTxtFormat = SimpleDateFormat("E")
            val cityTxtData = Date(dt.toLong() * 1000)
            return cityTxtFormat.format(cityTxtData)
        }
    }
}