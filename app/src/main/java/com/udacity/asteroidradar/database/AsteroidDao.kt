package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM DatabaseAsteroids WHERE closeApproachDate BETWEEN date(:from) AND date(:to) ORDER BY closeApproachDate ASC")
    fun getAsteroidsFromDb(from:String, to:String): LiveData<List<DatabaseAsteroids>> //from and to arguments pass in from repository.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseAsteroids)  //varrag takes a variable number of arguments
}