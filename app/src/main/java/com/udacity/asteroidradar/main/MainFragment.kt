package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    //Setup viewModel
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater) //Inflate with data binding

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the ViewModel
        binding.viewModel = viewModel

        //Add RecyclerView and configure to listen to click events.
        val adapter = AsteroidListAdapter(AsteroidListener{id -> viewModel.onAsteroidEntryClicked(id)})

        //Observe changes to the AsteroidsRepository and update the RecyclerView when changes are made.
        viewModel.asteroidList.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        binding.asteroidRecycler.adapter = adapter //Bind recycler in XML to the ListAdapter [RecyclerView adapter with the ViewHolder]

        //Observe LiveData navigateToAsteroidEntry and navigate to DetailFragment.
        viewModel.navigateToAsteroidEntry.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(it))
                viewModel.onNavigated()
            }
        })



        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }


}
