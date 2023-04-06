package com.example.wheatherforcast.favourites.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherforcast.MainRule
import com.example.wheatherforcast.fakerepos.FakeFavRepository
import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavouriteViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainRule()



    lateinit var viewModel: FavouriteViewModel
    lateinit var repository: FakeFavRepository

    @Before
    fun setUp() {
        repository = FakeFavRepository()
        viewModel = FavouriteViewModel(repository)

    }

    @Test
    fun getFavLocation_get2itemsfromdb() = mainCoroutineRule.runBlockingTest{

       // Given a list
        var model1 = FavModel("123456", "1234567.0", "en", "metric")
        var model2 = FavModel("123456", "1234567.0", "ar", "standard")
        var list = listOf<FavModel>(model1,model2)
        var result = false
        repository.insert(model1)
        repository.insert(model2)

        //when call getAllfav method from viewmodel

        viewModel._mutableList?.collect{
            if (list.equals(it))
                result=true
        }
        assertThat(result, `is`(true))



    }

    @Test
    fun insertnewFavourite() = mainCoroutineRule.runBlockingTest {

        //Given one favModel
        var model1 = FavModel("123456", "1234567.0", "en", "metric")
        var model2 = FavModel("123456", "1234567.0", "ar", "standard")
        var list = listOf<FavModel>(model1,model2)
        var result=false

        // when call add method
        viewModel.insertnewFavourite(model1)
        viewModel.insertnewFavourite(model2)

         viewModel._mutableList?.collect{
            if (list==it)
                result=true
        }

        assertThat(result, `is`(true))
    }

    @Test
    fun deleteLocation()= mainCoroutineRule.runBlockingTest {
        //Given a favmodel to delete
        var model1 = FavModel("123456", "1234567.0", "en", "metric")
        var model2 = FavModel("123456", "1234567.0", "ar", "standard")

        var list = listOf<FavModel>(model1,model2)
        var result=false

        repository.insert(model1)
        repository.insert(model2)

        // when call delete method
        viewModel.deleteLocation(model1)
        viewModel._mutableList?.collect{
           if (it?.size==1)
               result=true

        }

        assertThat(result, `is`(true))
    }
}