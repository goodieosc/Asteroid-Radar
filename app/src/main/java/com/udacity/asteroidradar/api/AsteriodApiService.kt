package com.udacity.asteroidradar.api


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.nasa.gov/"

//Use Retrofit Builder with ScalarsConverterFactory and BASE_URL
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// Implement the NeoWs [Near Earth Object Web Service] Interface with @Get getProperties returning a String.
interface neowsApiService {
    @GET("neo/rest/v1/feed") //BASE_URL is appended with apiParams
    fun getProperties(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String): Call<String>
}

// Create the NeoWs object using Retrofit to implement the NeoWs Service.
object neowsApi {
    val retrofitService : neowsApiService by lazy {retrofit.create(neowsApiService::class.java)}
}



