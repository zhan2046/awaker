package com.ruzhan.day

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awaker.common.util.ResUtils
import com.google.gson.internal.LinkedTreeMap
import com.ruzhan.day.db.entity.DayNew
import com.ruzhan.day.network.DayRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import kotlin.collections.ArrayList


class DayViewModel : ViewModel() {

    companion object {
        private const val START_PAGE = 1
        private const val VER = "android"
        private const val APP_VER = "36"
    }

    val refreshDayNewLiveData = MutableLiveData<List<DayNew>>()
            .also { it.value = null }
    val loadStatusLiveData = MutableLiveData<Boolean>()
            .also {
                it.value = false
            }

    val tagListLiveData = MutableLiveData<List<String>>()
    private val compositeDisposable = CompositeDisposable()

    private val refreshDayTagMap = LinkedTreeMap<String, ArrayList<DayNew>>(
            Comparator<String> { o1, o2 ->
                return@Comparator o1.compareTo(o2)
            })

    private val newListTab = ResUtils.getString(R.string.day_new_list_tab)

    init {
        compositeDisposable.add(DayRepository.get().loadDayNewList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { throwable -> throwable.printStackTrace() }
                .doOnNext { dayNewList ->
                    Log.i("DayViewModel", "doOnNext loadDayNewList size:" + dayNewList.size)
                    refreshDayTagList(dayNewList)
                    refreshDayNewLiveData.value = dayNewList
                }
                .subscribe({}, {}))
    }

    fun getDayNewList() {
        compositeDisposable.add(DayRepository.get().getDayNewList(START_PAGE, VER, APP_VER)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { throwable -> throwable.printStackTrace() }
                .doOnSubscribe { loadStatusLiveData.value = true }
                .doFinally { loadStatusLiveData.value = false }
                .subscribe({}, {}))
    }

    private fun refreshDayTagList(list: List<DayNew>) {
        handleDayTagList(list, refreshDayTagMap)
    }

    private fun handleDayTagList(list: List<DayNew>,
                                 dayTagMap: LinkedTreeMap<String, ArrayList<DayNew>>) {
        dayTagMap.clear()
        dayTagMap[newListTab] = ArrayList(list)
        for (item in list) {
            val tags = item.tags
            if (tags.isNotEmpty()) {
                val tagsModel = tags[0]
                val tagName = tagsModel.name
                val tagNameList = tagName.split(" · ")
                if (tagNameList.isNotEmpty()) {
                    val tagKey = tagNameList[tagNameList.size - 1]
                    val tagValue = if (!dayTagMap.containsKey(tagKey))
                        ArrayList() else dayTagMap.getValue(tagKey)
                    if (!tagValue.contains(item)) {
                        tagValue.add(item)
                    }
                    dayTagMap[tagKey] = tagValue
                }
            }
        }
        val tagList = dayTagMap.keys
        tagListLiveData.value = ArrayList(tagList)
    }

    fun getRefreshTagDayModelList(tag: String): List<DayNew>? {
        return refreshDayTagMap[tag]
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}