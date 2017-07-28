package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.Comment;
import com.future.awaker.data.realm.CommentPageRealm;
import com.future.awaker.data.realm.CommentRealm;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.util.LogUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class NiceCommentViewModel extends BaseListViewModel {

    private static final String TAG = NiceCommentViewModel.class.getSimpleName();
    public static final String NICE_COMMENT = "nice_comment";

    public ObservableList<Comment> comments = new ObservableArrayList<>();
    private HashMap<String, String> map = new HashMap<>();

    public NiceCommentViewModel() {
        map.put(CommentPageRealm.ID, NICE_COMMENT);
    }

    @Override
    public void refreshData(boolean refresh) {
        if (refresh && comments.isEmpty()) {
            getLocalHotComment();
        }
        getRemoteHotComment();
    }

    private void getRemoteHotComment() {
        disposable.add(NewRepository.get().getHotComment(TOKEN)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setRemoteHotComment(result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setRemoteHotComment(List<Comment> commentList) {
        checkEmpty(commentList);
        if (!isEmpty.get()) {
            if (isRefresh) {
                comments.clear();

                CommentPageRealm commentPageRealm = new CommentPageRealm();
                commentPageRealm.setComment_page_id(NICE_COMMENT);
                RealmList<CommentRealm> realms = CommentPageRealm.getRealmList(commentList);
                commentPageRealm.setCommentList(realms);
                NewRepository.get().updateLocalRealm(commentPageRealm);
            }
            comments.addAll(commentList);
            isEmpty.set(true);
        }
    }

    private void getLocalHotComment() {
        disposable.add(NewRepository.get().getLocalRealm(CommentPageRealm.class, map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "doOnError: " + throwable.toString()))
                .doOnNext(realmResults -> {
                    LogUtils.d("getLocalHotComment" + realmResults.size());
                    setLocalHotComment(realmResults);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setLocalHotComment(RealmResults realmResults) {
        if (realmResults == null || realmResults.isEmpty()) {
            return;
        }
        CommentPageRealm commentPageRealm = (CommentPageRealm) realmResults.get(0);
        if (comments.isEmpty()) {   // data is empty, network not back
            RealmList<CommentRealm> commentRealms = commentPageRealm.getCommentList();
            List<Comment> commentList = CommentPageRealm.getList(commentRealms);

            checkEmpty(commentList);
            if (!isEmpty.get()) {
                if (isRefresh) {
                    comments.clear();
                }
                comments.addAll(commentList);
                isEmpty.set(true);
            }
        }
    }
}
