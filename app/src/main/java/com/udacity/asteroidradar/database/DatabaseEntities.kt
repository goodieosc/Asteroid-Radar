package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.ImageOfTheDay

// Create the DatabaseEntities class, adding annotations for the class and the primary key.
@Entity(tableName = "asteroids_table")
data class EntityDbTableAsteroids constructor(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)


// Add an extension function which converts from database objects to domain objects:
fun List<EntityDbTableAsteroids>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid (
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }

}

@Entity(tableName = "images_table")
data class EntityDbTableImageOfTheDay constructor(
    @PrimaryKey
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String)

// Add an extension function which converts from database objects to domain objects:
fun List<EntityDbTableImageOfTheDay>.asImageDomainModel(): List<ImageOfTheDay> {
    return map {
        ImageOfTheDay (
            copyright = it.copyright,
            date = it.date,
            explanation = it.explanation,
            hdurl = it.hdurl,
            media_type = it.media_type,
            service_version = it.service_version,
            title = it.title,
            url = it.url
        )
    }

}