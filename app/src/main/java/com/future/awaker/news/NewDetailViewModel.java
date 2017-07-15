package com.future.awaker.news;

import android.databinding.ObservableField;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.source.NewRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class NewDetailViewModel extends BaseListViewModel {

    public ObservableField<NewDetail> newDetail = new ObservableField<>();

    private String newId;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void fetchData(boolean isRefresh, int page) {
        if (isRunning.get()) {
            return;
        }
        disposable.add(NewRepository.get().getNewDetail(TOKEN, newId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> {
                    NewDetail newDetail = httpResult.getData();
                    notifyEmpty(newDetail);

                    if (!isEmpty.get()) {
                        this.newDetail.set(newDetail);
                    }
                })
                .subscribe());
    }

}
