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
    }

    val throwableLiveData = MutableLiveData<Throwable>()
    val dayNewLiveData = MutableLiveData<List<DayNewModel>>()

    fun getDayNewList(page: Int) {
        DayRepository.get().getDayNewList(page, VER, APP_VER)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { throwable -> throwableLiveData.value = throwable }
                .doOnSuccess { dayNewList -> dayNewLiveData.value = dayNewList }
                .subscribe(Subscriber.create())
    }
}