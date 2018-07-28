package com.future.awaker.news.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.Comment;
import com.future.awaker.data.other.RefreshListModel;
import com.future.awaker.db.entity.CommentEntity;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
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

public class NiceCommentViewModel extends BaseListViewModel {

    private static final String TAG = "NiceCommentViewModel";

    private RefreshListModel<Comment> refreshListModel = new RefreshListModel<>();
    private List<Comment> niceCommentList = new ArrayList<>();

    private MutableLiveData<RefreshListModel<Comment>> niceCommentLiveData = new MutableLiveData<>();

    private Disposable localDisposable;

    public NiceCommentViewModel() {
        niceCommentLiveData.setValue(null);
    }

    public void initLocalNiceCommentList() {
        localDisposable = AwakerRepository.get().loadCommentEntity(Comment.NICE_COMMENT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalNiceCommentList doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalNiceCommentList)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalNiceCommentList(CommentEntity commentEntity) {
        if (commentEntity != null && niceCommentList.isEmpty()) {
            niceCommentList.addAll(commentEntity.commentList);
            refreshListModel.setRefreshType(niceCommentList);
            niceCommentLiveData.setValue(refreshListModel);
        }
        localDisposable.dispose();
    }

    @Override
    public void refreshData(boolean refresh) {
        AwakerRepository.get().getHotComment(TOKEN)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                })
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setRefreshDataDoOnNext(refresh, result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setRefreshDataDoOnNext(boolean refresh, List<Comment> remoteCommentList) {
        if (refresh) {
            niceCommentList.clear();
            refreshListModel.setRefreshType();

        } else {
            refreshListModel.setUpdateType();
        }

        if (remoteCommentList == null || remoteCommentList.isEmpty()) {
            isEmpty.set(true);

        } else {
            isEmpty.set(false);
            niceCommentList.addAll(remoteCommentList);
        }

        refreshListModel.setList(niceCommentList);
        niceCommentLiveData.setValue(refreshListModel);

        setNiceCommentListLocalDb(niceCommentList);
    }

    private void setNiceCommentListLocalDb(List<Comment> localComments) {
        Flowable.create(e -> {
            CommentEntity commentEntity = new CommentEntity(Comment.NICE_COMMENT,
                    localComments);
            AwakerRepository.get().insertCommentEntity(commentEntity);
            e.onComplete();

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setNiceCommentListLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public MutableLiveData<RefreshListModel<Comment>> getNiceCommentLiveData() {
        return niceCommentLiveData;
    }
}
