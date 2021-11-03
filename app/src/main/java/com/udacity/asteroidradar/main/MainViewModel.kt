package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidsRepository
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.neowsApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    //Instantiate database under the application context
     private val database = getDatabase(application)

    //Instantiate the repository using the AsteroidsDatabase
    private val AsteroidsRepository = AsteroidsRepository(database)




    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        viewModelScope.launch {
            AsteroidsRepository.refreshAsteroids()  //Refresh the repository [Download new asteroids from NASA and store into the DB
        }
    }

    //Get the list of Asteroids from the AsteroidsRepository
    val asteroidList = AsteroidsRepository.asteroids

    //Private and exposed to observer for navigation event.
    private val _navigateToAsteroidEntry = MutableLiveData<Asteroid>()
    val navigateToAsteroidEntry: LiveData<Asteroid>
        get() = _navigateToAsteroidEntry


    fun onAsteroidEntryClicked(asteroid: Asteroid){
        _navigateToAsteroidEntry.value = asteroid
    }

    fun onNavigated() {
        _navigateToAsteroidEntry.value = null
    }

}