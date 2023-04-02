package com.example.wheatherforcast.favourites.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favourites" )
@Parcelize
data class FavModel (

    var latitude:String,
    var longitude:String,
    var cityName:String?,
    @PrimaryKey
    var longlat:String
    ) : Parcelable
