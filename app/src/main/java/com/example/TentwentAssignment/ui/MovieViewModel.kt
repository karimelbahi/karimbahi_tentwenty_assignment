package com.example.TentwentAssignment.ui.movie

import androidx.lifecycle.*
import com.example.TentwentAssignment.data.local.entity.ImageEntity
import com.example.TentwentAssignment.data.local.entity.MovieDetailEntity
import com.example.TentwentAssignment.data.local.entity.MovieEntity
import com.example.TentwentAssignment.data.local.entity.VideoEntity
import com.example.TentwentAssignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    private val _moviesLiveData: MutableLiveData<Resource<MovieEntity>> = MutableLiveData()
    private val _moviesDetailLiveData: MutableLiveData<Resource<MovieDetailEntity>> =
        MutableLiveData()
    private val _videoLiveData: MutableLiveData<Resource<VideoEntity>> = MutableLiveData()
    private val _imageLiveData: MutableLiveData<Resource<ImageEntity>> = MutableLiveData()

    fun getMovies(
        apiKey: String,
        language: String,
        page: String
    ) {

        viewModelScope.launch {
            repository.getMovies(apiKey, language, page)
                .onStart {
                    withContext(Dispatchers.Main) {
                        _moviesLiveData.value = Resource(
                            Resource.Status.LOADING,
                            null,
                            "Loading..."
                        )
                    }
                }.catch { error ->
                    withContext(Dispatchers.Main) {
                        _moviesLiveData.value = Resource(
                            Resource.Status.ERROR,
                            null,
                            error.message.toString()
                        )
                    }
                }.collect { result ->
                    withContext(Dispatchers.Main) {
                        _moviesLiveData.value = Resource(
                            Resource.Status.SUCCESS,
                            result.data,
                            "On movies fetched successfully."
                        )
                    }
                }
        }
    }

    fun getMovieDetail(
        apiKey: String,
        movieId: Int
    ) {
        viewModelScope.launch {
            repository.getMovieDetail(apiKey, movieId)
                .onStart {
                    withContext(Dispatchers.Main) {
                        _moviesDetailLiveData.value = Resource(
                            Resource.Status.LOADING,
                            null,
                            "Loading..."
                        )
                    }
                }.catch { error ->
                    withContext(Dispatchers.Main) {
                        _moviesDetailLiveData.value = Resource(
                            Resource.Status.ERROR,
                            null,
                            error.message.toString()
                        )
                    }
                }.collect { result ->
                    withContext(Dispatchers.Main) {
                        _moviesDetailLiveData.value = Resource(
                            Resource.Status.SUCCESS,
                            result.data,
                            "On movies detail fetched successfully."
                        )
                    }
                }
        }
    }

    fun getVideo(
        apiKey: String,
        movieId: Int
    ) {
        viewModelScope.launch {
            repository.getVideo(apiKey, movieId)
                .onStart {
                    withContext(Dispatchers.Main) {
                        _videoLiveData.value = Resource(
                            Resource.Status.LOADING,
                            null,
                            "Loading..."
                        )
                    }
                }.catch { error ->
                    withContext(Dispatchers.Main) {
                        _videoLiveData.value = Resource(
                            Resource.Status.ERROR,
                            null,
                            error.message.toString()
                        )
                    }
                }.collect { result ->
                    withContext(Dispatchers.Main) {
                        _videoLiveData.value = Resource(
                            Resource.Status.SUCCESS,
                            result.data,
                            "On video fetched successfully."
                        )
                    }
                }
        }
    }

    fun getImage(
        apiKey: String,
        movieId: Int
    ) {
        viewModelScope.launch {
            repository.getImage(apiKey, movieId)
                .onStart {
                    withContext(Dispatchers.Main) {
                        _imageLiveData.value = Resource(
                            Resource.Status.LOADING,
                            null,
                            "Loading..."
                        )
                    }
                }.catch { error ->
                    withContext(Dispatchers.Main) {
                        _imageLiveData.value = Resource(
                            Resource.Status.ERROR,
                            null,
                            error.message.toString()
                        )
                    }
                }.collect { result ->
                    withContext(Dispatchers.Main) {
                        _imageLiveData.value = Resource(
                            Resource.Status.SUCCESS,
                            result.data,
                            "On video fetched successfully."
                        )
                    }
                }
        }
    }


    val moviesLiveData: LiveData<Resource<MovieEntity>>
        get() = _moviesLiveData

    val moviesDetailLiveData: LiveData<Resource<MovieDetailEntity>>
        get() = _moviesDetailLiveData

    val videoLiveData: LiveData<Resource<VideoEntity>>
        get() = _videoLiveData

    val imageLiveData: LiveData<Resource<ImageEntity>>
        get() = _imageLiveData
}