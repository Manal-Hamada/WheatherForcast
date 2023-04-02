package com.example.wheatherforcast.home.model

import androidx.recyclerview.widget.DiffUtil

class WeatherDiffUtills:DiffUtil.ItemCallback<WeatherResponse>() {

    override fun areItemsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
        return oldItem.lat == newItem.lat
    }

    override fun areContentsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
        return oldItem == newItem
    }
}