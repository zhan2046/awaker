package com.future.awaker.video;

import android.databinding.Bindable;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.data.source.repository.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class SpecialListViewModel extends BaseListViewModel {

    private String id;
    private String title;
    private String content;
    private String url;

    @Bindable
    private SpecialDetail specialDetail;

    public SpecialDetail getSpecialDetail() {
        return specialDetail;
    }

    public void setSpecialDetail(SpecialDetail specialDetail) {
        this.specialDetail = specialDetail;
        title = specialDetail.title;
        url = specialDetail.cover;
        content = specialDetail.content;
        notifyChange();
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        if (specialDetail != null) {
            return specialDetail.title;
        }
        return title;
    }

    public String getUrl() {
        if (specialDetail != null) {
            return specialDetail.cover;
        }
        return url;
    }

    public void setParams(String id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    @Override
    public void refreshData(boolean refresh) {
        disposable.add(NewRepository.get()
                .getSpecialDetail(TOKEN, id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    SpecialDetail specialDetail = result.getData();
                    setSpecialDetail(specialDetail);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}
