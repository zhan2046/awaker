package com.ruzhan.awaker.article.comment

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.ruzhan.awaker.article.db.entity.CommentEntity
import com.ruzhan.awaker.article.model.Comment
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

class ArticleHotCommentViewModel(app: Application) : AndroidViewModel(app) {

    private val requestStatus: RequestStatus<List<Comment>> = RequestStatus()

    val loadStatusLiveData: MutableLiveData<LoadStatus> = MutableLiveData()
    val requestStatusLiveData: MutableLiveData<RequestStatus<List<Comment>>> = MutableLiveData()

    private var disposable: Disposable? = null

    init {
        requestStatusLiveData.value = null
    }

    fun loadLocalCommentList() {
        disposable = AwakerRepository.get().loadCommentEntity(Comment.NICE_COMMENT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnNext { commentEntity ->
                    if (requestStatusLiveData.value == null) {
                        requestStatus.refreshStatus = RequestStatus.REFRESH
                        requestStatus.data = commentEntity.commentList
                        requestStatusLiveData.value = requestStatus
                    }
                    disposable?.dispose()
                }
                .subscribe({ }, { })
    }

    fun getHotCommentList(refreshStatus: Int) {
        if (requestStatus.isNetworkRequest) return

        requestStatus.isNetworkRequest = true

        requestStatus.refreshStatus = refreshStatus
        requestStatus.setPage(refreshStatus)

        AwakerRepository.get().getHotComment(ConstantUtils.TOKEN)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { throwable -> throwable.printStackTrace() }
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
                .doOnNext { commentList ->
                    requestStatus.data = commentList
                    requestStatusLiveData.value = requestStatus
                    setHotCommentLocalDb(commentList!!)
                }
                .subscribe(Subscriber.create())
    }

    private fun setHotCommentLocalDb(localComments: List<Comment>) {
        Flowable.create<Any>({ e ->
            val commentEntity = CommentEntity(Comment.NICE_COMMENT,
                    localComments)
            AwakerRepository.get().insertCommentEntity(commentEntity)
            e.onComplete()

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnComplete { }
                .subscribe(Subscriber.create())
    }
}