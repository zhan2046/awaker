package com.future.awaker.video;

import android.databinding.Bindable;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.data.realm.SpecialDetailRealm;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.util.LogUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmResults;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class SpecialListViewModel extends BaseListViewModel {

    private static final String TAG = SpecialListViewModel.class.getSimpleName();

    private String id;
    private String title;
    private String content;
    private String url;

    @Bindable
    private SpecialDetail specialDetail;

    private HashMap<String, String> map = new HashMap<>();

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
        map.put(SpecialDetailRealm.ID, id);
    }

    @Override
    public void refreshData(boolean refresh) {
        if (isRefresh && specialDetail == null) {
            getLocalSpecialDetail();
        }
        getRemoteSpecialDetail();
    }

    private void getRemoteSpecialDetail() {
        disposable.add(NewRepository.get()
                .getSpecialDetail(TOKEN, id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setRemoteSpecialDetail(result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setRemoteSpecialDetail(SpecialDetail specialDetail) {
        checkEmpty(specialDetail);
        if (!isEmpty.get()) {
            if (isRefresh) {
                // save to local
                SpecialDetailRealm newDetailRealm = SpecialDetailRealm.setSpecialDetail(specialDetail);
                NewRepository.get().updateLocalRealm(newDetailRealm);
            }
            setSpecialDetail(specialDetail);
        }
    }

    private void getLocalSpecialDetail() {
        disposable.add(NewRepository.get().getLocalRealm(SpecialDetailRealm.class, map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "doOnError: " + throwable.toString()))
                .doOnNext(realmResults -> {
                    LogUtils.d("getLocalSpecialDetail" + realmResults.size());
                    setLocalSpecialDetail(realmResults);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setLocalSpecialDetail(RealmResults realmResults) {
        if (realmResults == null || realmResults.isEmpty()) {
            return;
        }
        SpecialDetailRealm commentHotRealm = (SpecialDetailRealm) realmResults.get(0);
        if (specialDetail == null) {   // data is empty, network not back
            SpecialDetail specialDetail = SpecialDetailRealm.setSpecialDetailRealm(commentHotRealm);
            setSpecialDetail(specialDetail);
        }
    }
}
