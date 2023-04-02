package com.example.wheatherforcast.favourites.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wheatherforcast.databinding.FavListItemBinding
import com.example.wheatherforcast.favourites.model.FavModel

class FavouriteAdapter(var onFavClickListener: onFavouriteClickListener,var c: Context):
    androidx.recyclerview.widget.ListAdapter<FavModel,FavouriteAdapter.FavouriteViewHolder>(
    FavDiffUtills() ){


    lateinit var binding: FavListItemBinding

    inner class FavouriteViewHolder(var binding:FavListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = FavListItemBinding .inflate(inflater, parent, false)
        return  FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val favItem= getItem(position)
        holder.binding.favItemTxt.text=favItem.cityName
        holder.binding.favCard.elevation=0f
        holder.binding.favCard.setBackgroundColor(Color.TRANSPARENT)
        holder.binding.deleteIcon.setOnClickListener {
            onFavClickListener.deletelocation(favItem)
        }
        holder.binding.favCard.setOnClickListener{
            onFavClickListener.goToLcation(favItem)
            println("from adapter"+favItem.latitude)
        }

    }
}