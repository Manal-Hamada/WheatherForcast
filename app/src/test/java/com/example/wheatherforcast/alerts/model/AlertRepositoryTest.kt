package com.example.wheatherforcast.alerts.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherforcast.MainRule
import com.example.wheatherforcast.db.alertdb.AlertsDataBase
import com.example.wheatherforcast.db.favouritedb.FavouriteDataBase
import com.example.wheatherforcast.fakerepos.FakeAlertRepository
import com.example.wheatherforcast.fakerepos.FakeFavRepository
import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.collection.IsEmptyCollection
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AlertRepositoryTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainRule()


    lateinit var db: AlertsDataBase
    lateinit var repository: FakeAlertRepository

    @Before
    fun setUp() {

        //   initialization Database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlertsDataBase::class.java
        ).allowMainThreadQueries().build()

        repository = FakeAlertRepository()
    }

    @After
    fun closeDB() {
        //   close Database
        db.close()
    }

    @Test
    fun getAlert_insertAlert_countAlerts() = runBlocking<Unit> {
        // Given
        var alert1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        var alert2 = AlertModel("2", "7.00", "1.00", "11/5","12/6")
        var result = 0
        repository.insertAlert(alert1)
        repository.insertAlert(alert2)

        // When
        repository.getAllAlerts()?.collect {
            result = it!!.size
        }

        // Then
        MatcherAssert.assertThat(result, CoreMatchers.`is`(2))

    }

    @Test
    fun insertAlert_insertInRoom_countAlerts() = runBlocking {
        // Given

        var alert1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        var alert2 = AlertModel("2", "7.00", "1.00", "11/5","12/6")

        var result: List<AlertModel>? = null
        // When
        repository.insertAlert(alert1)
        repository.insertAlert(alert2)

        // Then
        repository.getAllAlerts()?.collect {
            result = it
        }
        assertThat(result?.size, Is.`is`(2))
        assertThat(result!![0], IsNull.notNullValue())
        assertThat(result!![1], IsNull.notNullValue())

    }

    @Test
    fun deleteFAlert_insertInRoom_CheckListIsEmpty() = runBlocking {
        // Given
        var alert1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        var alert2 = AlertModel("2", "7.00", "1.00", "11/5","12/6")
        var result: List<AlertModel>? = null
        repository.insertAlert(alert1)
        repository.insertAlert(alert2)

        // When
        repository.deleteAlert(alert1)
        repository.deleteAlert(alert2)

        // Then
        repository.getAllAlerts()?.collect {
            result = it
        }
        MatcherAssert.assertThat(result?.size, Is.`is`(0))
        assertThat(result, IsEmptyCollection.empty())
    }
}
