package com.ruzhan.day

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.ruzhan.common.Subscriber
import com.ruzhan.day.model.DayNewModel
import com.ruzhan.day.source.DayRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class DayViewModel(app: Application) : AndroidViewModel(app) {

    companion object {
        private const val START_PAGE = 1
        private const val VER = "android"
        private const val APP_VER = "36"

        private const val REFRESH = "REFRESH"
        private const val LOAD_MORE = "LOAD_MORE"
    }

    var currentPage = START_PAGE
    var isLoading = false

    val refreshDayNewLiveData = MutableLiveData<List<DayNewModel>>()
    val loadMoreDayNewLiveData = MutableLiveData<List<DayNewModel>>()
    val loadStatusLiveData = MutableLiveData<Boolean>()

    fun refreshDayNewList() {
        getDayNewList(REFRESH)
    }

    fun loadMoreDayNewList() {
        getDayNewList(LOAD_MORE)
    }

    private fun getDayNewList(status: String) {
        if (isLoading) {
            return
        }
        isLoading = true
        currentPage = when (status) {
            REFRESH -> START_PAGE
            LOAD_MORE -> ++currentPage
            else -> START_PAGE
        }
        DayRepository.get().getDayNewList(currentPage, VER, APP_VER)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadStatusLiveData.value = true }
                .doFinally {
                    isLoading = false
                    loadStatusLiveData.value = false
                }
                .doOnSuccess { dayNewList ->
                    when (status) {
                        REFRESH -> refreshDayNewLiveData.value = dayNewList
                        LOAD_MORE -> loadMoreDayNewLiveData.value = dayNewList
                    }
                }
                .subscribe(Subscriber.create())
    }
}