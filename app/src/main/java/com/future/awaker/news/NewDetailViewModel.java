package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.Comment;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.realm.NewDetailRealm;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.util.LogUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmResults;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class NewDetailViewModel extends BaseListViewModel {

    private static final String TAG = NewDetailViewModel.class.getSimpleName();


    public ObservableField<NewDetail> newDetail = new ObservableField<>();
    public ObservableList<Comment> comments = new ObservableArrayList<>();

    private String newId;
    private String title;
    private String url;
    private HashMap<String, String> map = new HashMap<>();

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
        map.put(NewDetailRealm.ID, newId + NewDetailRealm.DETAIL);
    }

    public String getTitle() {
        return title;
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
        if (refresh && newDetail.get() == null) {
            getLocalNewDetail();
        }
        getRemoteNewDetail();


    }
    private void getLocalNewDetail() {
        disposable.add(NewRepository.get().getLocalNewDetail(map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "doOnError: " + throwable.toString()))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(realmResults -> {
                    LogUtils.d("getLocalNewList" + realmResults.size());
                    setLocalNewDetail(realmResults);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setLocalNewDetail(RealmResults realmResults) {
        if (realmResults == null || realmResults.isEmpty()) {
            return;
        }
        NewDetailRealm newDetailRealm = (NewDetailRealm) realmResults.get(0);
        if (newDetail.get() == null) {   // data is empty, network not back
            NewDetail newDetailItem = NewDetailRealm.setNewDetailRealm(newDetailRealm);
            setDataObject(newDetailItem, newDetail);
        }
    }

    private void getRemoteNewDetail() {
        disposable.add(NewRepository.get().getNewDetail(TOKEN, newId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> setRemoteNewDetail(httpResult.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setRemoteNewDetail(NewDetail newDetailItem) {
        checkEmpty(newDetailItem);
        if (!isEmpty.get()) {
            if (isRefresh) {
                // save to local
                NewRepository.get().updateLocalNewDetail(newDetailItem);
            }
            setDataObject(newDetailItem, newDetail);
        }
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
