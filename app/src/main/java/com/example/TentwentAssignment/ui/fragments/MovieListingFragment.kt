package com.example.TentwentAssignment.ui.movie.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.TentwentAssignment.data.remote.response.movie.MovieResponse
import com.example.TentwentAssignment.data.remote.response.movie.Result
import com.example.TentwentAssignment.databinding.FragmentMovieListingBinding
import com.example.TentwentAssignment.ui.movie.adapter.MovieAdapter
import com.example.TentwentAssignment.ui.movie.MovieViewModel
import com.example.TentwentAssignment.util.Constants
import com.example.TentwentAssignment.util.Resource
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieListingFragment : Fragment(), MovieAdapter.IMovie {

    val TAG: String = "MovieListingFragment"
    val viewModel: MovieViewModel by viewModels()
    @Inject lateinit var gson: Gson
    lateinit var binding: FragmentMovieListingBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListingBinding.inflate(layoutInflater)
        viewModel.moviesLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.d(TAG, "LOADING ${it.message}")
                    binding.progressBar.visibility = View.VISIBLE
                    binding.movieRv.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
                    Log.i(TAG, "SUCCESS ${it.data}")
                    it.data?.let { movieEntity ->
                        binding.movieRv.visibility = View.VISIBLE
                        val moviesResponse = gson.fromJson(movieEntity.response, MovieResponse::class.java)
                        binding.movieRv.adapter =  MovieAdapter(requireContext(),this@MovieListingFragment,moviesResponse.results)
                        binding.progressBar.visibility = View.GONE
                    }
                }
                Resource.Status.ERROR -> {
                    Log.e(TAG, "ERROR ${it.message}")
                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    Log.e(TAG,"INVALID - No status found.")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Call API
        viewModel.getMovies(
            apiKey = Constants.API_KEY,
            language = Constants.API_LANGUAGE,
            page = "1"
        )

    }

    override fun onMovieClick(item: Result, position: Int) {
        findNavController().navigate(
            MovieListingFragmentDirections.actionMovieListingFragmentToMovieDetailFragment(
                item.id
            )
        )
    }

    override fun onMovieBookClick(item: Result, position: Int) {
        findNavController().navigate(
            MovieListingFragmentDirections.actionMovieListingFragmentToMovieBookFragment(
                item.id
            )
        )
    }

}