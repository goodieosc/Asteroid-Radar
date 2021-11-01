package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding

//RecyclerView class
class AsteroidListAdapter: ListAdapter<Asteroid, AsteroidListAdapter.ViewHolder>(AsteroidDiffCallback()){

    //Mandatory overrides for ListAdapter
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    //Mandatory overrides for ListAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    //Specific ViewHolder. Can create more of these for different types of data.
    class ViewHolder private constructor (val binding: AsteroidListItemBinding): RecyclerView.ViewHolder(binding.root){
        val asteroidID: TextView = binding.asteroidIdTextView
        val asteroidImpactDate: TextView = binding.asteroidImpactDateTextView
        val asteroidDangerous: ImageView = binding.asteroidDangerousImage

        fun bind(item: Asteroid) {
            val res = itemView.context.resources
            asteroidID.text = item.id.toString()
            asteroidImpactDate.text = item.closeApproachDate

            if (item.isPotentiallyHazardous) {
                asteroidDangerous.setImageResource(R.drawable.ic_status_potentially_hazardous)
            } else asteroidDangerous.setImageResource(R.drawable.ic_status_normal)
        }

        //Inflate the template for this specific ViewHolder Type
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidListItemBinding.inflate(layoutInflater,parent,false) //Bind to the asteroid_list_item.xml
                return ViewHolder(binding)
            }
        }
    }

    //Diff util for determining is changes have been made to content, if so then they will be refreshed.
    class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id //Just compares the old and new ID.
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem //Compares all data within the item.
        }
    }
}


