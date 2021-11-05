package com.udacity.asteroidradar

import android.os.Parcelable
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
data class ImageOfTheDay(val copyright: String,
                         val date: String,
                         val explanation: String,
                         val hdurl: String,
                         val media_type: String,
                         val service_version: String,
                         val title: String,
                         val url: String) : Parcelable
