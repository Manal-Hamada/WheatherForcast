package com.example.wheatherforcast.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherforcast.alerts.model.AlertModel
import com.example.wheatherforcast.db.alertdb.AlertsDao
import com.example.wheatherforcast.db.alertdb.AlertsDataBase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsEmptyCollection
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AlertDaoTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var db: AlertsDataBase
    lateinit var dao: AlertsDao

    @Before
    fun initDB() {
        //   initialization Database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlertsDataBase::class.java
        )
            .allowMainThreadQueries().build()
        dao = db.alertDao()!!
    }

    @After
    fun closeDB() {
        //   close Database
        db.close()
    }

    @Test
    fun getFavourite_insertItems_countItems() = runBlocking {
        // Given
        var model1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        dao.insertAlert(model1)
        var model2 = AlertModel("2", "7.00", "1.00", "11/5","12/6")
        dao.insertAlert(model2)
        var result = 0

        // When
        dao.getAllAlerts()?.collect {
            result = it!!.size
        }

        // Then
        MatcherAssert.assertThat(result, `is`(2))
    }

    @Test
    fun insertFav_insertItem_returnItem() = runBlocking {
        // Given
        var model1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        var result: List<AlertModel>? = null
        //when call
        dao.insertAlert(model1)

        // Then
        dao.getAllAlerts()?.collect {
            result = it

        }
        assertThat(result?.size,`is`(1))
    }

    @Test
    fun deleteFav_deleteItem_checkIsNull() = runBlocking {
        // Given
        var model1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        var result: List<AlertModel>? = null
        dao.insertAlert(model1)

        // When
        dao.insertAlert(model1)
        // Then

        dao.getAllAlerts()?.collect {
            result = it

        }
       assertThat(result, IsEmptyCollection.empty())
       assertThat(result?.size, CoreMatchers.`is`(0))
    }
}