package com.ruzhan.day

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.ruzhan.common.Subscriber
import com.ruzhan.day.source.DayRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class DayViewModel(app: Application) : AndroidViewModel(app) {

    companion object {
        private const val START_PAGE = 1
        private const val VER = "android"
        private const val APP_VER = "36"
    }

    fun getDayNewList(page: Int) {
        DayRepository.get().getDayNewList(page, VER, APP_VER)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { t -> t.printStackTrace() }
                .doOnSuccess { dayNewList -> {} }
                .subscribe(Subscriber.create())
    }
}