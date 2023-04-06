package com.example.wheatherforcast.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherforcast.db.favouritedb.FavouriteDao
import com.example.wheatherforcast.db.favouritedb.FavouriteDataBase
import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsEmptyCollection
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavouriteDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var db: FavouriteDataBase
    lateinit var dao: FavouriteDao

    @Before
    fun initDB() {
        //   initialization Database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavouriteDataBase::class.java
        )
            .allowMainThreadQueries().build()
        dao = db.favouriteDao()!!
    }

    @After
    fun closeDB() {
        //   close Database
        db.close()
    }

    @Test
    fun getFavourite_insertItems_countItems() = runBlocking {
        // Given
        var model1 = FavModel("12345", "456778", "Cairo","12345456778")
        dao.insertLocation(model1)
        var model2 = FavModel("45678", "098798", "Alexandria","45678098798")
        dao.insertLocation(model2)
        var result=0

        // When
         dao.getAllFavourites()?.collect{
             result=it!!.size
        }
        // Then
       assertThat(result ,`is`(2))
    }

    @Test
    fun insertFav_insertItem_returnItem() = runBlocking {
        // Given
        var model1 = FavModel("12345", "456778", "Cairo", "12345456778")
        var result: List<FavModel>? = null
        //when call
        dao.insertLocation(model1)

        // Then
        dao.getAllFavourites()?.collect {
            result = it

        }
        assertThat(result?.size, `is`(1))
    }

    @Test
    fun deleteFav_deleteFavItem_checkIsNull() = runBlocking {
        // Given
         var model1 = FavModel("12345", "456778", "Cairo", "12345456778")
         var result:List<FavModel>?=null
        dao.insertLocation(model1)

        // When
        dao.deleteLocation(model1)
        // Then

        dao.getAllFavourites()?.collect {
         result=it

        }
        assertThat(result, IsEmptyCollection.empty())
        assertThat(result?.size,`is`(0))
    }
}
