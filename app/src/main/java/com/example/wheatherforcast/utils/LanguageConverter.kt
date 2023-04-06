package com.example.wheatherforcast.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

class LanguageConverter {

    companion object {
        fun checkLanguage(language: String?, context: Context) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
        /*   @SuppressLint("ObsoleteSdkInt")
        fun checkLanguage(localeCode: String, context: Context) {
            val resources = context.resources
            val dm = resources.displayMetrics
            val config: Configuration = resources.configuration
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            {
                config.setLocale(Locale(localeCode))
            }
            else
            {
                config.locale = Locale(localeCode)
            }
            resources.updateConfiguration(config, dm)
        }
    }*/
    }
}
