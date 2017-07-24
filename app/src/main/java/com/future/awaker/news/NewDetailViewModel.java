package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.Comment;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class NewDetailViewModel extends BaseListViewModel {

    public ObservableField<NewDetail> newDetail = new ObservableField<>();
    public ObservableList<Comment> comments = new ObservableArrayList<>();

    private String newId;
    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public void refreshData(boolean refresh) {
        disposable.add(NewRepository.get().getNewDetail(TOKEN, newId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> {
                    NewDetail newDetail = httpResult.getData();
                    checkEmpty(newDetail);

                    if (!isEmpty.get()) {
                        this.newDetail.set(newDetail);
                    }
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void getHotCommentList() {
        disposable.add(NewRepository.get().getUpNewsComments(TOKEN, newId)
                .doOnError(throwable -> {})
                .doOnSubscribe(disposable -> {})
                .doOnTerminate(() -> {})
                .doOnNext(result -> {
                    List<Comment> commentList = result.getData();
                    if (commentList != null && !commentList.isEmpty()) {
                        comments.clear();
                        comments.addAll(commentList);
                    }
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}
