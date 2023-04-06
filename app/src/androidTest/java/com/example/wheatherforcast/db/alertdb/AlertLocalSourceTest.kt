package com.example.wheatherforcast.db.alertdb

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
class AlertLocalSourceTest {

    private val  alerts= mutableListOf<AlertModel>()
    @Test
    fun insertAlert() {
        // Given an alert to be insert
        var alert1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        var alert2 = AlertModel("2", "7.00", "1.00", "11/5","12/6")
        var list:List<AlertModel>?=null

        // when call method add
        alerts.add(alert1)
        var result=alerts.size
        //then

        assertThat(result, `is`(1))
    }

    @Test
    fun deleteAlert() {
        // Given an alert  to be deleted
        var alert1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        var alert2 = AlertModel("2", "7.00", "1.00", "11/5","12/6")
        alerts.add(alert1)
        alerts.add(alert2)
        var list:List<AlertModel>?=null

        // when call method add
        alerts.remove(alert1)
        alerts.remove(alert2)
        var result=alerts.size
        //then

        assertThat(result, `is`(0))
    }

    @Test
    fun getAllAlerts() {
        // Given insert number of alerts
        var alert1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        var alert2 = AlertModel("2", "7.00", "1.00", "11/5","12/6")
        alerts.add(alert1)
        alerts.add(alert1)

        // when call method get
        var result= alerts.get(0).id
        var size=alerts.size
        //then

        assertThat(result, `is`("1"))
        assertThat(size, `is`(2))
    }
}