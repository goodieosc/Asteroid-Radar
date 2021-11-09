package com.udacity.asteroidradar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.EntityDbTableImageOfTheDay
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
    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAsteroidsFromDb(startDate, endDate)){ //Pass in arguments for query variables
        it.asDomainModel()
    }

    //Use Transformation.map to convert your LiveData list of DatabaseVideo objects to domain Video objects
    val imageEntry: LiveData<EntityDbTableImageOfTheDay> = database.imageOfTheDayDao.getImageDetailsFromDb()

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){ //Dispatcher IO is stating to run on disk, not ram

            try{
                val asteroidList = neowsApi.retrofitService.getProperties(startDate,endDate,apiKey) //Get the data from the network
                Timber.i("asteroidList - Success: $asteroidList")
                Log.i("AsteroidRepository","asteroidList - Success: $asteroidList")


                val formattedAsteroidsList = parseAsteroidsJsonResult(JSONObject(asteroidList))  //Transform JSON into formatted array list.
                Timber.i("formattedAsteroidsList - Success: $formattedAsteroidsList")

                //Save list to database
                database.asteroidDao.insertAll(*formattedAsteroidsList.asDatabaseModel().toTypedArray()) //Note the asterisk * is the spread operator. It allows you to pass in an array to a function that expects varargs.

                Timber.i("Success: $formattedAsteroidsList")

            } catch (e: Exception) {
                Timber.i("Failure: $e")
            }
        }
    }

    suspend fun refreshImages(){
        withContext(Dispatchers.IO){ //Dispatcher IO is stating to run on disk, not ram

            try{
                val asteroidImage = neowsApiImage.retrofitService.getImageProperties() //Get the data from the network
                Timber.i("Get asteroidImage details from NASA - Success: $asteroidImage")
                Log.i("AsteroidRepository","Get asteroidImage details from NASA - Success: $asteroidImage")


                //If image of the day is actually an image, save it to the database. If not, don't save to record.
                if (asteroidImage.media_type == "image"){
                    //Save record to database
                    database.imageOfTheDayDao.insertImageRecord(asteroidImage.asImageDatabaseModel())
                    Timber.i("Saved new Image record to DB: $asteroidImage")
                } else {
                    Log.i("AsteroidRepository","Todays entry is a video, not saved to database")
                    Timber.i("Todays entry is a video, not saved to database [Timber]")
                }

            } catch (e: Exception) {
                Timber.i("Failure: $e")
                Log.i("AsteroidRepository","Get asteroidImage details from NASA - Failure: $e")
            }
        }
    }




}