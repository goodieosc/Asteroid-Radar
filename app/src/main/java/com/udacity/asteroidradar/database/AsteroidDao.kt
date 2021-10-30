package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {
    @Query("select * from DatabaseAsteroids")
    fun getAsteroidsFromDb(): LiveData<List<DatabaseAsteroids>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseAsteroids)  //varrag takes a variable number of arguments
}