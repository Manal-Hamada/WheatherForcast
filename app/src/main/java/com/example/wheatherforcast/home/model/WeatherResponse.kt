package com.example.wheatherforcast.home.model

    data class WeatherResponse(
        val alerts: List<Alert>,
        val current: Current,
        val daily: List<Daily>,
        val hourly: List<Hourly>,
        val lat: Double,
        val lon: Double,
        val timezone: String,
        val timezone_offset: Int
    )

    data class Alert(
        val description: String,
        val end: Int,
        val event: String,
        val sender_name: String,
        val start: Int,
        val tags: List<String>
    )

    data class Current(
        val clouds: Int,
        val dew_point: Double,
        val dt: Int,
        val feels_like: Double,
        val humidity: Int,
        val pressure: Int,
        val sunrise: Int,
        val sunset: Int,
        val temp: Double,
        val uvi: Double,
        val visibility: Int,
        val weather: List<Weather>,
        val wind_deg: Int,
        val wind_speed: Double
    )

    data class Weather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String

        )

    data class Daily(
        val clouds: Int,
        val dew_point: Double,
        val dt: Int,
        val feels_like: FeelsLike,
        val humidity: Int,
        val moon_phase: Double,
        val moonrise: Int,
        val moonset: Int,
        val pop: Double,
        val pressure: Int,
        val rain: Double,
        val snow: Double,
        val sunrise: Int,
        val sunset: Int,
        val temp: Temp,
        val uvi: Double,
        val weather: List<Weather>,
        val wind_deg: Int,
        val wind_gust: Double,
        val wind_speed: Double
    )

    data class FeelsLike(
        val day: Double,
        val eve: Double,
        val morn: Double,
        val night: Double
    )

    data class Temp(
        val day: Double,
        val eve: Double,
        val max: Double,
        val min: Double,
        val morn: Double,
        val night: Double
    )

    data class Hourly(
        val dt: Int,
        val temp: Double,
        val feels_like: Double,
        val pressure: Int,
        val humidity: Int,
        val dew_point: Double,
        val uvi: Double,
        val clouds: Int,
        val wind_speed: Double,
        val wind_deg: Int,
        val wind_gust: Double,
        val pop: Double,
        val rain: Rain,
        val snow: Snow,
        val weather: List<Weather>,
    )

    data class Rain(
        val `1h`: Double
    )

    data class Snow(
        val `1h`: Double
    )


