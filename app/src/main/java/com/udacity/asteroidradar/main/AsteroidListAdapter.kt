package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.DatabaseAsteroids
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

        fun bind(item: Asteroid) {
            binding.asteroids = item
            binding.executePendingBindings()
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

    /**
     * Callback for calculating the diff between two non-null items in a list.
     *
     * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
     * list that's been passed to `submitList`.
     */
    class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id //Just compares the old and new ID.
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem //Compares all data within the item.
        }
    }
}


