package com.example.wheatherforcast.utils

import com.airbnb.lottie.LottieAnimationView
import com.example.wheatherforcast.R

class Icons {
        companion object {
            fun getSuitableIcon(img: String, lotti: LottieAnimationView) {
                when (img) {
                    "01d" -> lotti.setAnimation(R.raw.sun2)
                    "01n" -> lotti.setAnimation(R.raw.moon)

                    "02d" -> lotti.setAnimation(R.raw.sun_cloud)
                    "02n" -> lotti.setAnimation(R.raw.cloud_night)

                    "03d" -> lotti.setAnimation(R.raw.cloud)
                    "03n" -> lotti.setAnimation(R.raw.cloud)

                    "04d" -> lotti.setAnimation(R.raw.broken_cloud)
                    "04n" -> lotti.setAnimation(R.raw.broken_cloud)

                    "09d" -> lotti.setAnimation(R.raw.rain)
                    "09n" -> lotti.setAnimation(R.raw.rain)

                    "10d" -> lotti.setAnimation(R.raw.sun_cloud_rain)
                    "10n" -> lotti.setAnimation(R.raw.night_cloud_rain)

                    "11d" -> lotti.setAnimation(R.raw.storm)
                    "11n" -> lotti.setAnimation(R.raw.storm)

                    "13d" -> lotti.setAnimation(R.raw.storm)
                    "13n" -> lotti.setAnimation(R.raw.storm)

                    "50d" -> lotti.setAnimation(R.raw.mist)
                    "50n" -> lotti.setAnimation(R.raw.mist)
                    else -> print(" ")
                }
            }
        }
    }
