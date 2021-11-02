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
class AsteroidListAdapter(val clickListener: AsteroidListener): ListAdapter<Asteroid, AsteroidListAdapter.ViewHolder>(AsteroidDiffCallback()){

    //Mandatory overrides for ListAdapter
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener,getItem(position)!!)
    }

    //Mandatory overrides for ListAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    //Specific ViewHolder. Can create more of these for different types of data.[AsteroidListItemBinding relates to the xml of the template, asteroid_list_item.xml]
    class ViewHolder private constructor (val binding: AsteroidListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: AsteroidListener, item: Asteroid) {
            binding.asteroids = item //Bind to the data block in the XML file
            binding.clickListener = clickListener //Bind to the data block in the XML file
            binding.executePendingBindings()
        }

        //Inflate the template for this specific ViewHolder Type
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidListItemBinding.inflate(layoutInflater,parent,false)
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

//Listener class for navigating upon a click event.
class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit){
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}


