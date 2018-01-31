package com.future.awaker.news.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.Comment;
import com.future.awaker.data.other.RefreshListModel;
import com.future.awaker.db.entity.CommentEntity;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.news.listener.SendCommentListener;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright ©2017 by ruzhan
 */

public class CommentViewModel extends BaseListViewModel {

    private static final String TAG = CommentViewModel.class.getSimpleName();

    private String newId;
    private SendCommentListener listener;

    private RefreshListModel<Comment> refreshListModel = new RefreshListModel<>();
    private List<Comment> commentList = new ArrayList<>();
    private MutableLiveData<RefreshListModel<Comment>> commentLiveData = new MutableLiveData<>();

    private Disposable localDisposable;


    public CommentViewModel(String newId, SendCommentListener listener) {
        this.newId = newId;
        this.listener = listener;

        commentLiveData.setValue(null);
    }

    public void initLocalCommentList() {
        localDisposable = AwakerRepository.get().loadCommentEntity(newId + Comment.NEW_DETAIL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalCommentList doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalCommentList)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalCommentList(CommentEntity commentEntity) {
        if (commentEntity != null && commentList.isEmpty()) {
            commentList.addAll(commentEntity.commentList);
            refreshListModel.setRefreshType(commentList);
            commentLiveData.setValue(refreshListModel);
        }
        localDisposable.dispose();
    }

    @Override
    public void refreshData(boolean refresh) {
        AwakerRepository.get().getNewsComments(TOKEN, newId, page)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setRefreshDataDoOnNext(refresh, result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setRefreshDataDoOnNext(boolean refresh, List<Comment> comments) {
        if (refresh) {
            commentList.clear();
            refreshListModel.setRefreshType();

        } else {
            refreshListModel.setUpdateType();
        }

        if (comments == null || comments.isEmpty()) {
            isEmpty.set(true);

        } else {
            isEmpty.set(false);
            commentList.addAll(comments);
        }
        refreshListModel.setList(commentList);
        commentLiveData.setValue(refreshListModel);

        setCommentListLocalDb(commentList);
    }

    private void setCommentListLocalDb(List<Comment> localComments) {
        Flowable.create(e -> {
            CommentEntity commentEntity = new CommentEntity(newId + Comment.NEW_DETAIL,
                    localComments);
            AwakerRepository.get().insertCommentEntity(commentEntity);
            e.onComplete();

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setCommentListLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public void sendNewsComment(String newId, String content, String open_id,
                                String pid) {
        disposable.add(AwakerRepository.get().sendNewsComment(TOKEN, newId, content, open_id, pid)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "remote doOnError: "
                        + throwable.toString()))
                .doOnNext(result -> listener.sendCommentSuc())
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public MutableLiveData<RefreshListModel<Comment>> getCommentLiveData() {
        return commentLiveData;
    }
}
