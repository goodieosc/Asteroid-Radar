package com.udacity.asteroidradar.api


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.ImageOfTheDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.nasa.gov/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Use Retrofit Builder with ScalarsConverterFactory and BASE_URL
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

//Use Retrofit Builder with ScalarsConverterFactory and BASE_URL
private val retrofitMoshi = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// Implement the NeoWs [Near Earth Object Web Service] Interface with @Get getProperties returning a String.
interface neowsApiService {
    @GET("neo/rest/v1/feed") //BASE_URL is appended with apiParams
    suspend fun getProperties(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String): String
}


// Implement the NeoWs [Near Earth Object Web Service] Interface with @Get getProperties returning a String.
interface neowsApiServiceGetImageOfTheDay {
    @GET("planetary/apod?api_key=U9mndCIzdwnqbnnSEtmWHon1SHywWpkaKRBZsjec") //BASE_URL is appended with apiParams
    suspend fun getImageProperties(): ImageOfTheDay
}



// Create the NeoWs object using Retrofit to implement the NeoWs Service.
object neowsApi {
    val retrofitService : neowsApiService by lazy {retrofit.create(neowsApiService::class.java)}
}

// Create the NeoWs object using Retrofit to implement the NeoWs Service.
object neowsApiImage {
        val retrofitService : neowsApiServiceGetImageOfTheDay by lazy { retrofitMoshi.create(neowsApiServiceGetImageOfTheDay::class.java)}
}





