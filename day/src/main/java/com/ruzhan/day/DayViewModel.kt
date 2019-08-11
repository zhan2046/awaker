package com.ruzhan.day

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ruzhan.common.Subscriber
import com.ruzhan.common.util.ResUtils
import com.ruzhan.database.CommonModel
import com.ruzhan.day.model.DayNewModel
import com.ruzhan.day.network.DayRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


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

    val tagListLiveData = MutableLiveData<List<String>>()

    private val mainGSon = Gson()
    private var insetDayListFlow: Flowable<Any>? = null

    private var insetDisposable: Disposable? = null
    private var localDisposable: Disposable? = null
    private val compositeDisposable = CompositeDisposable()

    private val refreshDayTagMap = TreeMap<String, ArrayList<DayNewModel>>(Comparator<String> { o1, o2 ->
        return@Comparator o1.compareTo(o2)
    })
    private val loadMoreDayTagMap = TreeMap<String, ArrayList<DayNewModel>>(Comparator<String> { o1, o2 ->
        return@Comparator o1.compareTo(o2)
    })

    private val newListTab = ResUtils.getString(R.string.day_new_list_tab)

    init {
        insetDayListFlow = Flowable.create<Any>({ e ->
            val newList = refreshDayNewLiveData.value
            val dayListJson = mainGSon.toJson(newList)
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
                            refreshDayTagList(dayNewList)
                            refreshDayNewLiveData.value = dayNewList
                            insetDayListToDb()
                        }
                        LOAD_MORE -> {
                            loadMoreDayTagList(dayNewList)
                            loadMoreDayNewLiveData.value = dayNewList
                        }
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
        localDisposable = DayRepository.get().getCommonModel(DAY_LIST_ID)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnNext { commonModel ->
                    if (commonModel != null) {
                        val content = commonModel.content
                        if (content.isNotBlank()) {
                            val dayNewList: List<DayNewModel> = mainGSon.fromJson(content,
                                    object : TypeToken<List<DayNewModel>>() {}.type)
                            if (refreshDayNewLiveData.value == null) {
                                refreshDayTagList(dayNewList)
                                refreshDayNewLiveData.value = dayNewList
                            }
                        }
                    }
                    localDisposable?.dispose()
                }
                .subscribe({}, {})
        compositeDisposable.add(localDisposable!!)
    }

    private fun insetDayListToDb() {
        val insetDayListFlow = insetDayListFlow
        if (insetDayListFlow != null) {
            insetDisposable = insetDayListFlow.debounce(DEBOUNCE_DURATION, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(Throwable::printStackTrace)
                    .doOnComplete { insetDisposable?.dispose() }
                    .subscribe({}, {})
            compositeDisposable.add(insetDisposable!!)
        }
    }

    private fun refreshDayTagList(list: List<DayNewModel>) {
        handleDayTagList(list, refreshDayTagMap, REFRESH)
    }

    private fun loadMoreDayTagList(list: List<DayNewModel>) {
        handleDayTagList(list, loadMoreDayTagMap, LOAD_MORE)
    }

    private fun handleDayTagList(list: List<DayNewModel>,
                                 dayTagMap: TreeMap<String, ArrayList<DayNewModel>>,
                                 status: String) {
        dayTagMap.clear()
        dayTagMap[newListTab] = ArrayList(list)
        for (item in list) {
            val tags = item.tags
            if (tags != null && tags.isNotEmpty()) {
                val tagsModel = tags[0]
                val tagName = tagsModel.name
                if (tagName != null) {
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
        }
        if (status == REFRESH) {
            val tagList = dayTagMap.keys
            tagListLiveData.value = ArrayList(tagList)
        }
    }

    fun getRefreshTagDayModelList(tag: String): List<DayNewModel>? {
        return refreshDayTagMap[tag]
    }

    fun getLoadMoreTagDayModelList(tag: String): List<DayNewModel>? {
        return loadMoreDayTagMap[tag]
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}