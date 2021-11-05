package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidsRepository
import com.udacity.asteroidradar.api.neowsApiImage
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch

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
            loadImageOfTheDay()
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


    fun loadImageOfTheDay() {
        viewModelScope.launch {
            try {
                var asteroidImageUrl = neowsApiImage.retrofitService.getImageProperties() //Get the data from the network

//                val imageUrl = asteroidImageUrl
//                val imageView: ImageView = image
//                Picasso.get()
//                    .load(imageUrl)
//                    .into(imageView)

                Log.i("AsteroidsImage", "Success: ${asteroidImageUrl.size}")

            } catch (e: Exception) {
                Log.i("AsteroidsImage", "Failure: ${e.message}")
            }

        }
    }

    }


