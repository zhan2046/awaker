package com.ruzhan.day

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.internal.LinkedTreeMap
import com.ruzhan.day.db.entity.DayNew
import com.ruzhan.day.network.DayRepository
import com.ruzhan.day.util.ResUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class DayHomeModel : ViewModel() {

    companion object {
        private const val START_PAGE = 1
        private const val VER = "android"
        private const val APP_VER = "36"
    }

    val loadStatusLiveData = MutableLiveData<Boolean>()
        .also { it.value = false }
    val tagMapLiveData = MutableLiveData<LinkedTreeMap<String, String>>()

    private val dayTagMap = LinkedTreeMap<String, String>()
    private val firstTab = ResUtils.getString(R.string.day_new_list_tab)

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(DayRepository.get().loadDayNewList()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable -> throwable.printStackTrace() }
            .doOnNext { dayNewList ->
                Log.i("DayHomeModel", "loadDayNewList:" + dayNewList.size)
                updateDayTagList(dayNewList)
            }
            .subscribe({}, {}))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getDayNewList() {
        val loadStatus = loadStatusLiveData.value
        if (loadStatus == null || !loadStatus) {
            compositeDisposable.add(DayRepository.get().getDayNewList(START_PAGE, VER, APP_VER)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { throwable -> throwable.printStackTrace() }
                .doOnSubscribe { loadStatusLiveData.value = true }
                .doFinally { loadStatusLiveData.value = false }
                .doOnSuccess { dayNewList ->
                    Log.i("DayHomeModel", "getDayNewList:" + dayNewList.size)
                }
                .subscribe({}, {}))
        }
    }

    private fun updateDayTagList(dayNewList: List<DayNew>) {
        if (dayNewList.isNotEmpty()) {
            dayTagMap.clear()
            dayTagMap[firstTab] = ""
            for (index in dayNewList.size - 1 downTo 0) {
                val item = dayNewList[index]
                val tags = item.tags
                if (tags.isNotEmpty()) {
                    val tagsModel = tags[0]
                    val tagName = tagsModel.name
                    val tagNameList = tagName.split(" · ")
                    if (tagNameList.isNotEmpty()) {
                        val key = tagNameList[tagNameList.size - 1]
                        dayTagMap[key] = tagsModel.id
                    }
                }
            }
            Log.i("DayHomeModel", "dayTagMap:$dayTagMap")
            tagMapLiveData.value = dayTagMap
        }
    }
}