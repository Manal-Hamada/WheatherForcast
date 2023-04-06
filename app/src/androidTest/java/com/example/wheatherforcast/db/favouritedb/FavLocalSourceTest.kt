package com.example.wheatherforcast.db.favouritedb

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherforcast.alerts.model.AlertModel
import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavLocalSourceTest {

    private val  favLocations= mutableListOf<FavModel>()

    @Test
    fun insertToFav_insertFavLocation_countOfLocations() {

       // Given an favLocation item to be insert
        var location=FavModel("123456","1234567.0","cairo","metric")
        var list:List<FavModel>?=null
        // when call method add
        favLocations.add(location)
        var result=favLocations.size
        //then

        assertThat(result, `is`(1))

    }

    @Test
    fun deleteFav_getEmptyList() {
        // Given an favLocation item to be deleted
        var location1=FavModel("123456","1234567.0","cairo","metric")
        var location2=FavModel("123456","1234567.0","Alex","standard")
        favLocations.add(location1)
        favLocations.add(location2)
        var list:List<FavModel>?=null

        // when call method add
        favLocations.remove(location1)
        favLocations.remove(location2)
        var result=favLocations.size
        //then

        assertThat(result, `is`(0))

    }

    @Test
    fun getAllFavs() {

        // Given insert number of items
        var location1=FavModel("123456","1234567.0","cairo","metric")
        var location2=FavModel("123456","1234567.0","Alex","standard")
        favLocations.add(location1)
        favLocations.add(location2)

        // when call method get
       var result= favLocations.get(0).cityName
        var size=favLocations.size
        //then

        assertThat(result, `is`("cairo"))
        assertThat(size, `is`(2))
    }
}