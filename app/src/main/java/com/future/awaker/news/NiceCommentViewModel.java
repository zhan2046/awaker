package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.Comment;
import com.future.awaker.data.News;
import com.future.awaker.data.realm.CommentPageRealm;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Copyright ©2017 by Teambition
 */

public class NiceCommentViewModel extends BaseListViewModel {

    public static final String NICE_COMMENT = "nice_comment";

    public ObservableList<Comment> comments = new ObservableArrayList<>();
    private HashMap<String, String> map = new HashMap<>();

    public NiceCommentViewModel() {
        map.put(CommentPageRealm.ID, NICE_COMMENT);
    }

    @Override
    public void refreshData(boolean refresh) {
        disposable.add(NewRepository.get().getHotComment(TOKEN)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    List<Comment> commentList = result.getData();
                    checkEmpty(commentList);
                    if (!isEmpty.get()) {
                        if (isRefresh) {
                            comments.clear();
                        }
                        comments.addAll(commentList);
                        isEmpty.set(true);
                    }
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void getRemoteHotComment() {

    }

    private void getLocalHotComment() {

    }
}
