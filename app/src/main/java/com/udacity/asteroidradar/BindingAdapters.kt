package com.udacity.asteroidradar

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.database.EntityDbTableImageOfTheDay

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = "This asteroid is hazardous"
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = "This asteroid is safe"
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = "This asteroid is hazardous"
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = "This asteroid is safe"
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("mainImage")
fun bindImageViewToDisplayImageOfTheDay(imageView: ImageView, url: String) {

    try{
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)

        Log.i("BindingAdapters","Success: $url")

    } catch (e: Exception){

        Log.i("BindingAdapters","Failure: $e")
    }


}


