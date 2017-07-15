package com.future.awaker.video;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.Special;
import com.future.awaker.data.source.NewRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class VideoViewModel extends BaseListViewModel {

    public ObservableList<Special> specials = new ObservableArrayList<>();
    private int cat = Special.NORMAL;

    public void setCat(int cat) {
        this.cat = cat;
    }

    @Override
    public void fetchData(boolean isRefresh, int page) {
        if (isRunning.get()) {
            return;
        }
        disposable.add(NewRepository.get().getSpecialList(TOKEN, page, cat)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> {
                    List<Special> newList = httpResult.getData();
                    notifyEmpty(newList);

                    if (isRefresh) {
                        specials.clear();
                    }
                    if (!isEmpty.get()) {
                        specials.addAll(httpResult.getData());
                    }
                })
                .subscribe());
    }

}
