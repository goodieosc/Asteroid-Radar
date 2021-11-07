package com.udacity.asteroidradar.api

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.ImageOfTheDay
import com.udacity.asteroidradar.database.EntityDbTableAsteroids
import com.udacity.asteroidradar.database.EntityDbTableImageOfTheDay

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 */
@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroids: List<Asteroid>)

@JsonClass(generateAdapter = true)
data class Asteroid(val id: Long,
                    val codename: String,
                    val closeApproachDate: String,
                    val absoluteMagnitude: Double,
                    val estimatedDiameter: Double,
                    val relativeVelocity: Double,
                    val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean)


//create an extension function that converts from data transfer objects to database objects:
fun List<Asteroid>.asDatabaseModel(): List<EntityDbTableAsteroids> {
    return map {
        EntityDbTableAsteroids (
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous)
    }
}


//@JsonClass(generateAdapter = true)
//data class ImageOfTheDay(val copyright: String,
//                         val date: String,
//                         val explanation: String,
//                         val hdurl: String,
//                         val media_type: String,
//                         val service_version: String,
//                         val title: String,
//                         val url: String)
//
//
////create an extension function that converts from data transfer objects to database objects:
//fun List<ImageOfTheDay>.asDatabaseModel2(): List<EntityDbTableImageOfTheDay> {
//    return map {
//        EntityDbTableImageOfTheDay (
//            copyright = it.copyright,
//            date = it.date,
//            explanation = it.explanation,
//            hdurl = it.hdurl,
//            media_type = it.media_type,
//            service_version = it.service_version,
//            title = it.title,
//            url = it.url
//        )
//    }
//}