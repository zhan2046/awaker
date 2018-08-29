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

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleCommentListViewModel(app: Application) : AndroidViewModel(app) {

    companion object {

        private const val SEND_COMMENT = "SEND_COMMENT"
    }

    private val requestStatus: RequestStatus<List<Comment>> = RequestStatus()
    private var page: Int = 1

    val loadStatusLiveData: MutableLiveData<LoadStatus> = MutableLiveData()
    val sendCommentLiveData: MutableLiveData<String> = MutableLiveData()
    val requestStatusLiveData: MutableLiveData<RequestStatus<List<Comment>>> = MutableLiveData()

    private var disposable: Disposable? = null

    init {
        requestStatusLiveData.value = null
    }

    fun loadLocalCommentList(newId: String) {
        disposable = AwakerRepository.get().loadCommentEntity(newId + Comment.NEW_DETAIL)
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

    fun getCommentList(refreshStatus: Int, newId: String) {
        if (requestStatus.isNetworkRequest) return

        requestStatus.isNetworkRequest = true

        requestStatus.refreshStatus = refreshStatus
        requestStatus.setPage(refreshStatus)

        page = if (RequestStatus.REFRESH == requestStatus.refreshStatus) 1 else (++page)

        AwakerRepository.get().getNewsComments(ConstantUtils.TOKEN, newId, page)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
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

                    commentList?.let { setCommentListLocalDb(commentList, newId) }
                }
                .subscribe(Subscriber.create())
    }

    fun sendNewsComment(newId: String, content: String, open_id: String,
                        pid: String) {
        AwakerRepository.get().sendNewsComment(ConstantUtils.TOKEN, newId, content, open_id, pid)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnNext { sendCommentLiveData.value = SEND_COMMENT }
                .subscribe(Subscriber.create())
    }

    private fun setCommentListLocalDb(localComments: List<Comment>, newId: String) {
        Flowable.create<Any>({ e ->
            val commentEntity = CommentEntity(newId + Comment.NEW_DETAIL,
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