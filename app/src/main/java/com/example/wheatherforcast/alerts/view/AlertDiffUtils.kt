package com.example.wheatherforcast.alerts.view


import androidx.recyclerview.widget.DiffUtil
import com.example.wheatherforcast.alerts.model.AlertModel

class AlertDiffUtils:DiffUtil.ItemCallback<AlertModel>() {
    override fun areItemsTheSame(oldItem: AlertModel, newItem: AlertModel): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: AlertModel, newItem: AlertModel): Boolean {
        return oldItem==newItem
    }


}