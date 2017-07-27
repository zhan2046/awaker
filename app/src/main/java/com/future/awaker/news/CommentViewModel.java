package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.Comment;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Copyright ©2017 by Teambition
 */

public class CommentViewModel extends BaseListViewModel {

    private String newId;
    public ObservableList<Comment> comments = new ObservableArrayList<>();

    public void setNewId(String newId) {
        this.newId = newId;
    }

    @Override
    public void refreshData(boolean refresh) {
        disposable.add(NewRepository.get().getNewsComments(TOKEN, newId, page)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setDataList(result.getData(), comments))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}
