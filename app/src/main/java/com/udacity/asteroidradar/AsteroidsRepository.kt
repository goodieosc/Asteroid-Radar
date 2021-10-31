package com.udacity.asteroidradar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.neowsApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository(private val database: AsteroidsDatabase) {

    //Use Transformation.map to convert your LiveData list of DatabaseVideo objects to domain Video objects
    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.AsteroidDao.getAsteroidsFromDb()){
        it.asDomainModel()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    val dates = getNextSevenDaysFormattedDates()
    val startDate = dates[0]
    val endDate = dates[7]


    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){ //Dispatcher IO is stating to run on disk, not ram
            //Get the data from the network and then put it in the database
            val asteroidList = neowsApi.retrofitService.getProperties(startDate,endDate,"U9mndCIzdwnqbnnSEtmWHon1SHywWpkaKRBZsjec")
            val formattedAsteroidsList = parseAsteroidsJsonResult(JSONObject(asteroidList))

            database.AsteroidDao.insertAll(*formattedAsteroidsList.asDatabaseModel().toTypedArray()) //Note the asterisk * is the spread operator. It allows you to pass in an array to a function that expects varargs.


        }
    }
}