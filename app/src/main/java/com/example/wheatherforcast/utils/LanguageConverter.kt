package com.example.wheatherforcast.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

class LanguageConverter {

    companion object {
        fun checkLanguage(language: String?,context:Context) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
           context .resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }
}