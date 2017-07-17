package com.future.awaker.video;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    public void scrollToTop(RecyclerView recyclerView) {
        if (isRefresh) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            manager.scrollToPosition(0);
        }
    }

    @Override
    public void refreshData(boolean refresh) {
        disposable.add(NewRepository.get().getSpecialList(TOKEN, page, cat)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> {
                    List<Special> newList = httpResult.getData();
                    checkEmpty(newList);

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
