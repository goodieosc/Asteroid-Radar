package com.udacity.asteroidradar

import android.os.Parcelable
import com.udacity.asteroidradar.database.EntityDbTableImageOfTheDay
import com.udacity.asteroidradar.database.ImageOfTheDayDao
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(val id: Long,
                    val codename: String,
                    val closeApproachDate: String,
                    val absoluteMagnitude: Double,
                    val estimatedDiameter: Double,
                    val relativeVelocity: Double,
                    val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable

@Parcelize
data class ImageOfTheDay(val date: String,
                         val explanation: String,

                         val media_type: String,
                         val service_version: String,
                         val title: String,
                         val url: String) : Parcelable


//create an extension function that converts from data transfer objects to database objects:
fun ImageOfTheDay.asImageDatabaseModel(): EntityDbTableImageOfTheDay {
        return EntityDbTableImageOfTheDay (
            date = date,
            explanation = explanation,

            media_type = media_type,
            service_version = service_version,
            title = title,
            url = url
        )
    }



