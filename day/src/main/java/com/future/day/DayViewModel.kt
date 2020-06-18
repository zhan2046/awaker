package com.future.day

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.future.day.db.entity.DayNew
import com.future.day.network.DayRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class DayViewModel : ViewModel() {

    val refreshDayNewLiveData = MutableLiveData<List<DayNew>>()
        .also { it.value = null }
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun loadDayNewList(tagKey: String) {
        val flowAble = if (tagKey.isNotBlank())
            DayRepository.get().loadDayNewList(tagKey) else DayRepository.get().loadDayNewList()
        compositeDisposable.add(flowAble
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable -> throwable.printStackTrace() }
            .doOnNext { dayNewList ->
                Log.i("ViewModel", "loadDayNewList tagKey:" + tagKey +
                    " size:" + dayNewList.size)
                refreshDayNewLiveData.value = dayNewList
            }
            .subscribe({}, {}))
    }
}