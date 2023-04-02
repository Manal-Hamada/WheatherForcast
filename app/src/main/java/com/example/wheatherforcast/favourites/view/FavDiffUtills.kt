package com.example.wheatherforcast.favourites.view

import androidx.recyclerview.widget.DiffUtil
import com.example.wheatherforcast.favourites.model.FavModel

class FavDiffUtills:DiffUtil.ItemCallback<FavModel>() {

        override fun areItemsTheSame(oldItem: FavModel, newItem: FavModel): Boolean {
            return oldItem.longlat==newItem.longlat
        }

        override fun areContentsTheSame(oldItem: FavModel, newItem: FavModel): Boolean {
            return oldItem==newItem
        }

    }
