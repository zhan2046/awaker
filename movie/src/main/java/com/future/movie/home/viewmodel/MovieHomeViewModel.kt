package com.future.movie.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.future.common.CommonUtils
import com.future.movie.R
import com.future.movie.db.entity.MovieEntity
import com.future.movie.network.MovieRepository
import com.future.movie.utils.LionUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class MovieHomeViewModel : ViewModel() {

    val titleListLiveData = MutableLiveData<List<String>>()
    val loadStatusLiveData = MutableLiveData<Boolean>()

    private val firstTag = CommonUtils.get().getString(R.string.lion_tab_tag_new)
    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(MovieRepository.get().loadMovieEntityList()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable -> throwable.printStackTrace() }
            .doOnNext { movieList ->
                Log.i("MovieHomeViewModel", "loadMovieEntityList:" + movieList.size)
                updateMovieListTag(movieList)
            }
            .subscribe({}, {}))
    }

    fun getMovieList() {
        val pageFileName = "1".plus(LionUtils.JSON_FILE)
        compositeDisposable.add(MovieRepository.get().getMovieList(pageFileName)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadStatusLiveData.value = true }
            .doFinally { loadStatusLiveData.value = false }
            .doOnError { throwable -> throwable.printStackTrace() }
            .subscribe({}, {}))
    }

    private fun updateMovieListTag(movieList: List<MovieEntity>?) {
        if (movieList != null && movieList.isNotEmpty()) {
            val titleList = ArrayList<String>()
            titleList.add(firstTag)
            for (item in movieList) {
                val tag = item.type
                if (!titleList.contains(tag)) {
                    titleList.add(tag)
                }
            }
            titleListLiveData.value = titleList
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}