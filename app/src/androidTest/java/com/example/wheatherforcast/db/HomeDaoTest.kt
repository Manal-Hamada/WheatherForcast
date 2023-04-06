package com.example.wheatherforcast.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherforcast.db.homedb.HomeDao
import com.example.wheatherforcast.db.homedb.HomeDatabase
import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.home.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var db: HomeDatabase
    lateinit var dao: HomeDao

    @Before
    fun initDB() {
        //   initialization Database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HomeDatabase::class.java
        )
            .allowMainThreadQueries().build()
        dao = db.homeDao()!!
    }

    @After
    fun closeDB() {
        //   close Database
        db.close()
    }

    @Test
    fun getWeather_insertItems_timezone_offset() = runBlocking {
        // Given
        val response1 = WeatherResponse(null, null, null, null, 1223456.0, 123456.0, "", 12)
        var response2 = WeatherResponse(null, null, null, null, 1223456.0, 123456.0, "", 12)
        dao.insertWeatherResponse(response1)

        // dao.insertWeatherResponse(response2)
        var result = 0

        // When
        dao.getWeatherResponse()?.collect {
            result = it!!.timezone_offset
        }

        // Then
        assertThat(result, `is`(12))
    }

    @Test
    fun insertWeatherResponse_insertItemResponse_returnResponseLatitude() = runBlocking {
        // Given
        val listWeather: List<Weather> = emptyList()
        val listDaily: List<Daily> = emptyList()
        val listHourly: List<Hourly> = emptyList()
        var current = Current(0, 0.0, 1, 2.0, 2, 1, 1, 0, 4.0, 14.0, 5,
            listWeather, 1, 0.0)

        val data = WeatherResponse(null, current, listDaily, listHourly,
            0.0, 0.0,  "Egpt", 1)

        val response1 = WeatherResponse(null, null, null, null, 1223456.0, 123456.0, "egypt", 12)
        var result: WeatherResponse? = null
        //when call
        dao.insertWeatherResponse(response1)

        // Then
        dao.getWeatherResponse()?.collect {
            result = it

        }
        assertThat(result?.lat.toString(), `is`("1223456.0"))
        assertThat(result?.alerts, `is`(null))

    }
}