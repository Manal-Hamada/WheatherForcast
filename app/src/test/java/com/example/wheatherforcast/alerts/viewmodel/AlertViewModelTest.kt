package com.example.wheatherforcast.alerts.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherforcast.MainRule
import com.example.wheatherforcast.alerts.model.AlertModel
import com.example.wheatherforcast.fakerepos.FakeAlertRepository
import com.example.wheatherforcast.fakerepos.FakeFavRepository
import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.favourites.viewmodel.FavouriteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest= Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AlertViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainRule()


    lateinit var viewModel: AlertViewModel
    lateinit var repository: FakeAlertRepository

    @Before
    fun setUp() {
        repository = FakeAlertRepository()
        viewModel = AlertViewModel(repository)

    }

    @Test
    fun getAlerts() = mainCoroutineRule.runBlockingTest{

        // Given a list of Alerts
        var model1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        var model2 = AlertModel("2", "7.00", "1.00", "11/5","12/6")
        var list = listOf<AlertModel>(model1,model2)
        var result = false
        repository.insertAlert (model1)
        repository.insertAlert(model2)

        //when call getAllalerts method from viewmodel

        viewModel._mutableList?.collect{
            if (list.equals(it))
                result=true
        }
        //then
        assertThat(result, CoreMatchers.`is`(true))



    }

    @Test
    fun insertNewAlert() = mainCoroutineRule.runBlockingTest {

        // Given a list of Alerts
        var model1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        var model2 = AlertModel("2", "7.00", "1.00", "11/5","12/6")
        var list = listOf<AlertModel>(model1,model2)
        var result = false

        repository.insertAlert (model1)
        repository.insertAlert(model2)

        // when call add  alert method
        viewModel.insertNewAlert(model1)
        viewModel.insertNewAlert(model2)

        viewModel._mutableList?.collect{
            if (list[0].equals(model1))
                result=true
        }

        assertThat(result, CoreMatchers.`is`(true))
    }

    @Test
    fun deleteAlert() = mainCoroutineRule.runBlockingTest {
        //Given an alert to delete

        var model1 = AlertModel("1", "12.00", "1.00", "11/2","12/2")
        var model2 = AlertModel("2", "7.00", "1.00", "11/5","12/6")
        var list = listOf<AlertModel>(model1,model2)

        var result=false

        repository.insertAlert(model1)
        repository.insertAlert(model2)

        // when call delete method
        viewModel.deleteAlert(model1)
        viewModel._mutableList?.collect{
            if (it?.size==1)
                result=true

        }

        assertThat(result, CoreMatchers.`is`(true))
    }
}