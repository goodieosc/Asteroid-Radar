package com.udacity.asteroidradar.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.neowsApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel : ViewModel() {

    //Internal property
    private val _asteriods = MutableLiveData<ArrayList<Asteroid>>()
    //External property
    val asteriods: LiveData<ArrayList<Asteroid>>
        get() = _asteriods


    init {
        getAsteroidList()

    }

    @RequiresApi(Build.VERSION_CODES.N) //Annotation for the dates below function below.
    private fun getAsteroidList(){

        val dates = getNextSevenDaysFormattedDates()
        val startDate = dates[0]
        val endDate = dates[7]

        viewModelScope.launch {
            try{
                val listResult = neowsApi.retrofitService.getProperties(startDate,endDate,"U9mndCIzdwnqbnnSEtmWHon1SHywWpkaKRBZsjec")
                _asteriods.value = parseAsteroidsJsonResult(listResult)
                Log.i("MainViewModel","Success: ${_asteriods.value.toString()}")
            } catch (e: Exception) {
                Log.i("MainViewModel","Failure: ${_asteriods.value.toString()}")
            }

        }

//        neowsApi.retrofitService.getProperties(startDate,endDate,"U9mndCIzdwnqbnnSEtmWHon1SHywWpkaKRBZsjec").enqueue(object : retrofit2.Callback<String> {
//
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                _asteriods.value = response.body()?.let { parseAsteroidsJsonResult(JSONObject(it)) }
//                Log.i("MainViewModel","Success: ${_asteriods.value.toString()}")
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                //_asteriods.value = "Failure: " + t.message.to
//                Log.i("MainViewModel","Failure: ${_asteriods.value.toString()}")
//            }
//
//        })
    }
}