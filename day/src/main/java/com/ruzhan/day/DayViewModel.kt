package com.ruzhan.day

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ruzhan.common.Subscriber
import com.ruzhan.database.CommonModel
import com.ruzhan.day.model.DayNewModel
import com.ruzhan.day.source.DayRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class DayViewModel(app: Application) : AndroidViewModel(app) {

    companion object {
        private const val START_PAGE = 1
        private const val DAY_LIST_ID = 1000
        private const val DEBOUNCE_DURATION = 3000L
        private const val VER = "android"
        private const val APP_VER = "36"

        private const val REFRESH = "REFRESH"
        private const val LOAD_MORE = "LOAD_MORE"
    }

    var currentPage = START_PAGE
    var isLoading = false

    val refreshDayNewLiveData = MutableLiveData<List<DayNewModel>>().also { it.value = null }
    val loadMoreDayNewLiveData = MutableLiveData<List<DayNewModel>>().also { it.value = null }
    val loadStatusLiveData = MutableLiveData<Boolean>()

    private val gson = Gson()
    private var insetDayListFlowable: Flowable<Any>? = null

    init {
        insetDayListFlowable = Flowable.create<Any>({ e ->
            val newList = refreshDayNewLiveData.value
            val dayListJson = gson.toJson(newList)
            val commonModel = CommonModel(DAY_LIST_ID, dayListJson)
            DayRepository.get().insertCommonModel(commonModel)
            e.onComplete()

        }, BackpressureStrategy.LATEST)
    }

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
                .map { dayNewList -> filterNullTagList(ArrayList(dayNewList)) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadStatusLiveData.value = true }
                .doFinally {
                    isLoading = false
                    loadStatusLiveData.value = false
                }
                .doOnSuccess { dayNewList ->
                    when (status) {
                        REFRESH -> {
                            refreshDayNewLiveData.value = dayNewList
                            insetDayListToDb()
                        }
                        LOAD_MORE -> loadMoreDayNewLiveData.value = dayNewList
                    }
                }
                .subscribe(Subscriber.create())
    }

    private fun filterNullTagList(dayNewList: ArrayList<DayNewModel>): List<DayNewModel> {
        val removeList = ArrayList<DayNewModel>()
        for (dayNew in dayNewList) {
            if (dayNew.tags == null) {
                removeList.add(dayNew)
            }
        }
        if (removeList.isNotEmpty()) {
            dayNewList.removeAll(removeList)
        }
        return dayNewList
    }

    fun getLocalDayList() {
        DayRepository.get().getCommonModel(DAY_LIST_ID)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnNext { commonModel ->
                    if (commonModel != null) {
                        val content = commonModel.content
                        if (content.isNotBlank()) {
                            val dayNewList: List<DayNewModel> = gson.fromJson(content,
                                    object : TypeToken<List<DayNewModel>>() {}.type)
                            if (refreshDayNewLiveData.value == null) {
                                refreshDayNewLiveData.value = dayNewList
                            }
                        }
                    }
                }
                .subscribe(Subscriber.create())
    }

    private fun insetDayListToDb() {
        val insetDayListFlowable = insetDayListFlowable
        if (insetDayListFlowable != null) {
            insetDayListFlowable.debounce(DEBOUNCE_DURATION, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(Throwable::printStackTrace)
                    .doOnComplete { }
                    .subscribe(Subscriber.create())
        }
    }
}