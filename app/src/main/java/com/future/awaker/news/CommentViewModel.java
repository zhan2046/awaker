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

public class CommentViewModel extends BaseListViewModel {

    private static final String TAG = CommentViewModel.class.getSimpleName();

    private String newId;
    public ObservableList<Comment> comments = new ObservableArrayList<>();
    private HashMap<String, String> map = new HashMap<>();


    public void setNewId(String newId) {
        this.newId = newId;
        map.put(CommentPageRealm.ID, newId + CommentPageRealm.COMMENT_PAGE);
    }

    @Override
    public void refreshData(boolean refresh) {
        if (refresh && comments.isEmpty()) {
            getLocalCommentList();
        }
        getRemoteCommentList();
    }

    private void getRemoteCommentList() {
        disposable.add(NewRepository.get().getNewsComments(TOKEN, newId, page)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setRemoteHotCommentList(result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setRemoteHotCommentList(List<Comment> commentList) {
        setDataList(commentList, comments);
        if (!isEmpty.get()) {
            CommentPageRealm commentPageRealm = new CommentPageRealm();
            commentPageRealm.setComment_page_id(newId + CommentPageRealm.COMMENT_PAGE);
            RealmList<CommentRealm> realms = CommentPageRealm.getRealmList(commentList);
            commentPageRealm.setCommentList(realms);
            NewRepository.get().updateLocalRealm(commentPageRealm);
        }
    }

    private void getLocalCommentList() {
        disposable.add(NewRepository.get().getLocalRealm(CommentPageRealm.class, map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "doOnError: " + throwable.toString()))
                .doOnNext(realmResults -> {
                    LogUtils.d("getLocalNewList" + realmResults.size());
                    setLocalCommentList(realmResults);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setLocalCommentList(RealmResults realmResults) {
        if (realmResults == null || realmResults.isEmpty()) {
            return;
        }
        CommentPageRealm commentPageRealm = (CommentPageRealm) realmResults.get(0);
        if (comments.isEmpty()) {   // data is empty, network not back
            RealmList<CommentRealm> commentRealms = commentPageRealm.getCommentList();
            List<Comment> commentList = CommentPageRealm.getList(commentRealms);
            setDataList(commentList, comments);
        }
    }
}
