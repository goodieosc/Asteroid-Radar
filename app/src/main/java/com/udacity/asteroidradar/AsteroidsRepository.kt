package com.udacity.asteroidradar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

class AsteroidsRepository(private val database: AsteroidsDatabase) {

    //Get today's date and the date 7 days from now
    @RequiresApi(Build.VERSION_CODES.N)
    val dates = getNextSevenDaysFormattedDates()
    val startDate = dates[0]
    val endDate = dates[7]

    val apiKey = BuildConfig.API_KEY //Get API key from gradle.properties file

    //Use Transformation.map to convert your LiveData list of DatabaseVideo objects to domain Video objects
    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.AsteroidDao.getAsteroidsFromDb(startDate, endDate)){ //Pass in arguments for query variables
        it.asDomainModel()
    }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){ //Dispatcher IO is stating to run on disk, not ram

            try{
                val asteroidList = neowsApi.retrofitService.getProperties(startDate,endDate,apiKey) //Get the data from the network
                Timber.i("asteroidList - Success: $asteroidList")

                val formattedAsteroidsList = parseAsteroidsJsonResult(JSONObject(asteroidList))  //Transform JSON into formatted array list.
                Timber.i("formattedAsteroidsList - Success: $formattedAsteroidsList")

                //Save list to database
                database.AsteroidDao.insertAll(*formattedAsteroidsList.asDatabaseModel().toTypedArray()) //Note the asterisk * is the spread operator. It allows you to pass in an array to a function that expects varargs.

                Timber.i("Success: $formattedAsteroidsList")

            } catch (e: Exception) {
                Timber.i("Failure: $e")
            }
        }
    }


}