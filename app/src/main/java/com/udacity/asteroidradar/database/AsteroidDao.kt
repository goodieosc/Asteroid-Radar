package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.ImageOfTheDay

@Dao
interface AsteroidDao {
    //DatabaseAsteroids queries
    @Query("SELECT * FROM asteroids_table WHERE closeApproachDate BETWEEN date(:from) AND date(:to) ORDER BY closeApproachDate ASC")
    fun getAsteroidsFromDb(from:String, to:String): LiveData<List<EntityDbTableAsteroids>> //from and to arguments pass in from repository.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: EntityDbTableAsteroids)  //varrag takes a variable number of arguments

}

@Dao
interface ImageOfTheDayDao {
    //ImageOfTheDayTable queries
    @Query("SELECT * FROM images_table")
    fun getImageDetailsFromDb(): LiveData<EntityDbTableImageOfTheDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImageRecord(image: EntityDbTableImageOfTheDay)  //varrag takes a variable number of arguments

}
