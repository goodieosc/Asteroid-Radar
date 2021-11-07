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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidsRepository
import com.udacity.asteroidradar.api.neowsApiImage
import com.udacity.asteroidradar.database.EntityDbTableImageOfTheDay
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

        viewModelScope.launch {
            AsteroidsRepository.refreshImages()
        }


    }

    //Get the list of Asteroids from the AsteroidsRepository
    val asteroidList = AsteroidsRepository.asteroids

    //Get the image details from the AsteroidsRepository
    val imageEntry = AsteroidsRepository.imageEntry

    //Private and exposed to observer for navigation event.
    private val _imageUrl = MutableLiveData<EntityDbTableImageOfTheDay>()
    val imageUrl: LiveData<EntityDbTableImageOfTheDay>
        get() = _imageUrl



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


    fun downloadImage(imageView: ImageView,asteroidImage: EntityDbTableImageOfTheDay){
        viewModelScope.launch {


        }

    }


}




