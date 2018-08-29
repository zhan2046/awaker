package com.ruzhan.awaker.article.news

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.ruzhan.awaker.article.model.News
import com.ruzhan.awaker.article.source.AwakerRepository
import com.ruzhan.awaker.article.util.ConstantUtils
import com.ruzhan.lion.model.LoadStatus
import com.ruzhan.lion.model.RequestStatus
import com.ruzhan.lion.rx.Subscriber
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by ruzhan123 on 2018/8/28.
 */
class ArticleNewAllViewModel(app: Application) : AndroidViewModel(app) {

    companion object {

        const val NEW_ID: Int = 0
    }

    private val requestStatus: RequestStatus<List<News>> = RequestStatus()
    private var page: Int = 1

    val loadStatusLiveData: MutableLiveData<LoadStatus> = MutableLiveData()
    val requestStatusLiveData: MutableLiveData<RequestStatus<List<News>>> = MutableLiveData()

    private var disposable: Disposable? = null

    init {
        requestStatusLiveData.value = null
    }

    fun loadLocalNews() {
        disposable = AwakerRepository.get().loadNewsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnNext {newsList ->
                    if (requestStatusLiveData.value == null) {
                        requestStatus.refreshStatus = RequestStatus.REFRESH
                        requestStatus.data = newsList
                        requestStatusLiveData.value = requestStatus
                    }
                    disposable?.dispose()
                }
                .subscribe({ }, { })
    }

    fun getNewsAllList(refreshStatus: Int) {
        if (requestStatus.isNetworkRequest) return

        requestStatus.isNetworkRequest = true

        requestStatus.refreshStatus = refreshStatus
        requestStatus.setPage(refreshStatus)

        page = if (RequestStatus.REFRESH == requestStatus.refreshStatus) 1 else (++page)

        AwakerRepository.get().getNewList(ConstantUtils.TOKEN, page, NEW_ID)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {}
                .doOnSubscribe {
                    if (RequestStatus.REFRESH == requestStatus.refreshStatus) {
                        loadStatusLiveData.value = LoadStatus.LOADING
                    }
                }
                .map { result -> result.data }
                .doFinally {
                    loadStatusLiveData.value = LoadStatus.LOADED
                    requestStatus.isNetworkRequest = false
                }
                .doOnNext { newsList ->
                    requestStatus.data = newsList
                    requestStatusLiveData.value = requestStatus

                    newsList?.let { setNewsToLocalDb(newsList) }
                }
                .subscribe(Subscriber.create())
    }

    private fun setNewsToLocalDb(localNewsList: List<News>) {
        Flowable.create<Any>({ e ->
            AwakerRepository.get().insertNewsList(ArrayList<News>(localNewsList))
            e.onComplete()

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {}
                .doOnComplete { }
                .subscribe(Subscriber.create())
    }
}