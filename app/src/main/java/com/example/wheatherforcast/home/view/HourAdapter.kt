package com.example.wheatherforcast.home.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.wheatherforcast.databinding.HourItemBinding
import com.example.wheatherforcast.home.model.WeatherResponse
import com.example.wheatherforcast.utils.ConvertUnits
import com.example.wheatherforcast.utils.Icons
import com.example.wheatherforcast.utils.TimeConverter
import java.util.*

class HourAdapter(var weatherResponse: WeatherResponse, var tempUnit: String) :
    RecyclerView.Adapter<HourAdapter.HourlyViewHolder>() {

    lateinit var binding: HourItemBinding

    inner class HourlyViewHolder(var binding: HourItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = HourItemBinding.inflate(inflater, parent, false)
        return HourlyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 23
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {

        if (weatherResponse != null) {
            Icons.getSuitableIcon(
                weatherResponse!!.hourly[position].weather[0].icon,
                holder.binding.hourlyListImg
            )
            holder.binding.itemDgreeTv.text = ConvertUnits.convertTemp(
                weatherResponse!!.hourly[position].temp,
                tempUnit = tempUnit
            )
            holder.binding.itemHourTv.text= TimeConverter.getCurrentTime(weatherResponse!!.hourly[position].dt, weatherResponse!!.timezone)

        }

    }


}