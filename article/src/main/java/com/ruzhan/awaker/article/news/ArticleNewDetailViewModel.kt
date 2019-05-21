package com.ruzhan.awaker.article.news

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.ruzhan.awaker.article.db.entity.CommentEntity
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.awaker.article.model.NewDetail
import com.ruzhan.awaker.article.model.NewEle
import com.ruzhan.awaker.article.source.AwakerRepository
import com.ruzhan.awaker.article.util.ConstantUtils
import com.ruzhan.awaker.article.util.HtmlParser
import com.ruzhan.lion.model.LoadStatus
import com.ruzhan.lion.model.RequestStatus
import com.ruzhan.lion.rx.Subscriber
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticleNewDetailViewModel(app: Application) : AndroidViewModel(app) {

    companion object {
        private const val SEND_COMMENT = "SEND_COMMENT"
    }

    private val requestStatus: RequestStatus<NewDetail> = RequestStatus()

    val loadStatusLiveData: MutableLiveData<LoadStatus> = MutableLiveData()
    val requestStatusLiveData: MutableLiveData<RequestStatus<NewDetail>> = MutableLiveData()
    val commentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData()
    val newElesLiveData: MutableLiveData<List<NewEle>> = MutableLiveData()
    val sendCommentLiveData: MutableLiveData<String> = MutableLiveData()


    private var disposable: Disposable? = null
    private var commentDisposable: Disposable? = null

    init {
        requestStatusLiveData.value = null
        commentsLiveData.value = null
    }

    fun loadLocalCommentList(id: String) {
        commentDisposable = AwakerRepository.get().loadCommentEntity(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnNext { commentEntity ->
                    if (commentsLiveData.value == null) {
                        commentsLiveData.value = commentEntity.commentList
                    }
                    disposable?.dispose()
                }
                .subscribe({ }, { })
    }

    fun loadLocalNewDetail(id: String) {
        disposable = AwakerRepository.get().loadNewsDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnNext { newDetail ->
                    if (requestStatusLiveData.value == null) {
                        requestStatus.refreshStatus = RequestStatus.REFRESH
                        requestStatus.data = newDetail
                        requestStatusLiveData.value = requestStatus
                    }
                    disposable?.dispose()
                }
                .subscribe({ }, { })
    }

    fun getNewDetail(refreshStatus: Int, newId: String) {
        if (requestStatus.isNetworkRequest) return

        requestStatus.isNetworkRequest = true

        requestStatus.refreshStatus = refreshStatus
        requestStatus.setPage(refreshStatus)

        AwakerRepository.get().getNewDetail(ConstantUtils.TOKEN, newId)
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
                .doOnNext { newDetail ->
                    requestStatus.data = newDetail
                    requestStatusLiveData.value = requestStatus

                    newDetail?.let { setNewDetailLocalDb(newDetail) }
                }
                .subscribe(Subscriber.create())
    }

    fun getHotCommentList(newId: String) {
        AwakerRepository.get().getUpNewsComments(ConstantUtils.TOKEN, newId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .map { result -> result.data }
                .doOnNext { commentList ->
                    commentsLiveData.value = commentList
                    commentList?.let {
                        setCommentListLocalDb(it, newId)
                    }
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

    private fun setNewDetailLocalDb(newDetail: NewDetail) {
        Flowable.create<Any>({ e ->
            AwakerRepository.get().insertNewsDetail(newDetail)
            e.onComplete()

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnComplete { }
                .subscribe(Subscriber.create())
    }

    private fun setCommentListLocalDb(comments: List<Comment>, newId: String) {
        Flowable.create<Any>({ e ->
            val commentEntity = CommentEntity(newId, comments)
            AwakerRepository.get().insertCommentEntity(commentEntity)
            e.onComplete()

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnComplete { }
                .subscribe(Subscriber.create())
    }

    fun articleHtmlToModelList(html: String) {
        Flowable.create<List<NewEle>>({ e ->
            val newEleList = HtmlParser.htmlToList(html)
            e.onNext(newEleList)
            e.onComplete()

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnNext { newEleList ->
                    newElesLiveData.value = newEleList
                }
                .doOnComplete { }
                .subscribe(Subscriber.create())
    }
}