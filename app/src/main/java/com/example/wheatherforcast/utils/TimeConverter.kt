package com.example.wheatherforcast.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class TimeConverter {

    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentTime(dt: Int, timezone: String, format: String = "K:mm a"): String {

            val zoneId = ZoneId.of(timezone)
            val instant = Instant.ofEpochSecond(dt.toLong())
            val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
            return instant.atZone(zoneId).format(formatter)
        }
    }
}