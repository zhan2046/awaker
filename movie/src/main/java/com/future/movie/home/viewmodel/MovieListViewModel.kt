package com.future.movie.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.future.movie.db.entity.MovieEntity
import com.future.movie.network.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class MovieListViewModel : ViewModel() {

    val movieListLiveData = MutableLiveData<List<MovieEntity>>()
    private val compositeDisposable = CompositeDisposable()

    fun loadMovieEntityList(tag: String) {
        val flowAble = if (tag.isBlank())
            MovieRepository.get().loadMovieEntityList() else
            MovieRepository.get().loadMovieEntityList(tag)
        compositeDisposable.add(flowAble
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable -> throwable.printStackTrace() }
            .doOnNext { movieList ->
                Log.i("MovieListViewModel", "loadMovieEntityList tag:" +
                    tag + " size: " + movieList.size)
                movieListLiveData.value = movieList.shuffled()
            }
            .subscribe({}, {}))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}