package com.future.movie.detail.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.future.movie.db.entity.MovieDetailEntity
import com.future.movie.network.MovieRepository
import com.future.movie.utils.LionUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MovieDetailViewModel : ViewModel() {

    val movieDetailLiveData = MutableLiveData<MovieDetailEntity>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getMovieDetail(id: String) {
        loadMovieDetailEntity(id)
        updateMovieDetail(id)
    }

    private fun loadMovieDetailEntity(id: String) {
        compositeDisposable.add(MovieRepository.get().loadMovieDetailEntity(id)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable -> throwable.printStackTrace() }
            .doOnNext { movieDetail ->
                Log.i("MovieDetailViewModel", "loadMovieDetailEntity called...")
                movieDetailLiveData.value = movieDetail
            }
            .subscribe({}, {}))
    }

    private fun updateMovieDetail(id: String) {
        val detailFile = id.plus(LionUtils.JSON_FILE)
        compositeDisposable.add(MovieRepository.get().getMovieDetail(detailFile)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable -> throwable.printStackTrace() }
            .doOnSubscribe {}
            .doFinally {}
            .doOnSuccess {}
            .subscribe({}, {}))
    }
}