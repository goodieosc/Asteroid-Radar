package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.api.neowsApi
import retrofit2.Call
import retrofit2.Response


class MainViewModel : ViewModel() {

    //Internal property
    private val _asteriods = MutableLiveData<String>()
    //External property
    val asteriods: LiveData<String>
        get() = _asteriods

    init {
        getAsteroidList()

    }


    private fun getAsteroidList(){
        neowsApi.retrofitService.getProperties().enqueue(object : retrofit2.Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                _asteriods.value = "Failure: " + t.message
                Log.i("MainViewModel","Failure: ${_asteriods.value.toString()}")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                _asteriods.value = response.body()
                Log.i("MainViewModel","Success: ${_asteriods.value.toString()}")
            }
        })
    }

}