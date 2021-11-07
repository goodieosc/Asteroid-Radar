package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidsRepository
import com.udacity.asteroidradar.api.neowsApiImage
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch
import timber.log.Timber


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
//        viewModelScope.launch {
//            AsteroidsRepository.refreshImage()  //Refresh the image of the day [Download new image from NASA and store into the DB
//        }

    }

    //Get the list of Asteroids from the AsteroidsRepository
    val asteroidList = AsteroidsRepository.asteroids

    //Private and exposed to observer for navigation event.
    private val _navigateToAsteroidEntry = MutableLiveData<Asteroid>()
    val navigateToAsteroidEntry: LiveData<Asteroid>
        get() = _navigateToAsteroidEntry


    fun onAsteroidEntryClicked(asteroid: Asteroid) {
        _navigateToAsteroidEntry.value = asteroid
    }

    fun onNavigated() {
        _navigateToAsteroidEntry.value = null
    }


    fun loadImageOfTheDay(imageView: ImageView, titleTextView: TextView) {
        viewModelScope.launch {
            try {
                val asteroidImage =
                    neowsApiImage.retrofitService.getImageProperties() //Get the data from the network

                Glide.with(imageView.context)
                    .load(asteroidImage.url)
                    .into(imageView)

                imageView.contentDescription = asteroidImage.explanation
                titleTextView.text = asteroidImage.title

                Timber.i( "Success: ${asteroidImage.url}")

            } catch (e: Exception) {
                Timber.i( "Failure: ${e.message}")
            }

        }
    }
}




