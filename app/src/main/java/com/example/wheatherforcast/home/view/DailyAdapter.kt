package com.example.wheatherforcast.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wheatherforcast.utils.ConvertUnits
import com.example.wheatherforcast.utils.Icons
import com.example.wheatherforcast.databinding.DaysItemBinding
import com.example.wheatherforcast.home.model.WeatherResponse
import com.example.wheatherforcast.utils.DayFormatter

class DailyAdapter (var weatherResponse: WeatherResponse, var tempUnit:String): RecyclerView.Adapter<DailyAdapter.DailyViewHolder>(){

    lateinit var binding:DaysItemBinding

    inner class DailyViewHolder(var binding:DaysItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DaysItemBinding.inflate(inflater, parent, false)
        return DailyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weatherResponse?.daily?.size ?: 2
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {

        if (weatherResponse !=null){
            holder.binding.dayItemStatus.text=weatherResponse!!.daily!![position].weather[0].description
            Icons.getSuitableIcon(
                weatherResponse!!.daily!![position].weather[0].icon,
                holder.binding.imageView
            )
            holder.binding.dayItemTv.text = DayFormatter.getDay(weatherResponse!!.daily!![position].dt)
            holder.binding.dayItemDgree.text = ConvertUnits.convertTemp(weatherResponse!!.daily!![position].temp.day, tempUnit = tempUnit)

        }

    }


}