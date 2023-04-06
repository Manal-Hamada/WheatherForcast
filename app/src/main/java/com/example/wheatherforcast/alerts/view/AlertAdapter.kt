package com.example.wheatherforcast.alerts.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wheatherforcast.alerts.model.AlertModel
import com.example.wheatherforcast.databinding.AlertListItemBinding

class AlertAdapter(var onAlertClickListener: onAlertClickListener, var c: Context):
    androidx.recyclerview.widget.ListAdapter<AlertModel, AlertAdapter.AlertViewHolder>(
        AlertDiffUtils() ){

    lateinit var binding: AlertListItemBinding

    inner class AlertViewHolder(var binding:  AlertListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = AlertListItemBinding .inflate(inflater, parent, false)
        return  AlertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val alert= getItem(position)
        holder.binding.tvTimeStart.text=alert.timeStart
        holder.binding.tvDateStart.text= getItem(position).dateStart
        holder.binding.tvTimeEnd.text=getItem(position).timeEnd
        holder.binding.tvDateEnd.text= getItem(position).dateEnd
        holder.binding.delete.setOnClickListener {
            onAlertClickListener.deleteAlert(alert)
        }
    }


}