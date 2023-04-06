package com.example.wheatherforcast.favourites.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherforcast.MainRule
import com.example.wheatherforcast.db.favouritedb.FavouriteDataBase
import com.example.wheatherforcast.fakerepos.FakeFavDataSourse
import com.example.wheatherforcast.fakerepos.FakeFavRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
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
class FavouriteRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainRule()


    lateinit var db: FavouriteDataBase
    lateinit var repository: FakeFavRepository

    @Before
    fun setUp(){

        //   initialization Database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavouriteDataBase::class.java
        ).allowMainThreadQueries().build()

        repository = FakeFavRepository()
    }

    @After
    fun closeDB() {
        //   close Database
        db.close()
    }

    @Test
    fun getFavourite_insertInFavourite_countLoations() = runBlocking<Unit> {
        // Given
        var model1=FavModel("123456","1234567.0","cairo","metric")
        var model2=FavModel("123456","1234567.0","Alex","standard")
        var result=0
        repository.insert(model1)
        repository.insert(model2)

        // When
         repository.getAllFavs()?.collect{
             result=it!!.size
         }

        // Then
        MatcherAssert.assertThat(result, `is`(2))

    }
    @Test
    fun insertFavourite_insertInRoom_countLocations() = runBlocking{
        // Given
        var model1=FavModel("123456","1234567.0","cairo","metric")
        var model2=FavModel("123456","1234567.0","Alex","standard")
        var result:List<FavModel>?=null
        // When
        repository.insert(model1)
        repository.insert(model2)

        // Then
        repository.getAllFavs()?.collect{
            result=it
        }
       assertThat(result?.size, Is.`is`(2))
       assertThat(result!![0], IsNull.notNullValue())
       assertThat(result!![1], IsNull.notNullValue())

    }
    @Test
    fun deleteFavLocations_insertInRoom_CheckListIsEmpty() = runBlocking{
        // Given
        var model1=FavModel("123456","1234567.0","cairo","metric")
        var model2=FavModel("123456","1234567.0","Alex","standard")
        var result:List<FavModel>?=null
        repository.insert(model1)
        repository.insert(model2)

        // When
        repository.delete(model1)
        repository.delete(model2)

        // Then
        repository.getAllFavs()?.collect{
            result=it
        }
        MatcherAssert.assertThat(result?.size, Is.`is`(0))
        assertThat(result, IsEmptyCollection.empty())
    }
}