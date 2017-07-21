package com.future.awaker.video;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.Special;
import com.future.awaker.data.realm.SpecialPageRealm;
import com.future.awaker.data.realm.SpecialRealm;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class VideoViewModel extends BaseListViewModel {

    public ObservableList<Special> specials = new ObservableArrayList<>();
    private int cat = Special.NORMAL;
    private HashMap<String, String> map = new HashMap<>();

    public VideoViewModel() {
        map.put(SpecialPageRealm.CAT, String.valueOf(cat));
    }

    public void setCat(int cat) {
        this.cat = cat;
        map.clear();
        map.put(SpecialPageRealm.CAT, String.valueOf(cat));
    }

    public void scrollToTop(RecyclerView recyclerView) {
        if (isRefresh) {
            LinearLayoutManager manager =
                    (LinearLayoutManager) recyclerView.getLayoutManager();
            manager.scrollToPosition(0);
        }
    }

    @Override
    public void refreshData(boolean refresh) {
        if (refresh && specials.isEmpty()) {
            getLocalSpecialList();
        }
        getRemoteSpecialList();
    }

    private void getRemoteSpecialList() {
        disposable.add(NewRepository.get().getSpecialList(TOKEN, page, cat)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> setRemoteSpecialList(httpResult.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void getLocalSpecialList() {
        disposable.add(NewRepository.get().getLocalSpecialList(map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::setLocalNewList)
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setRemoteSpecialList(List<Special> specialList) {
        checkEmpty(specialList);
        if (!isEmpty.get()) {
            if (isRefresh) {
                // save to local
                NewRepository.get().updateLocalSpecialList(String.valueOf(cat), specialList);
            }
            setSpecialList(specialList);
        }
    }

    private void setLocalNewList(RealmResults realmResults) {
        if (realmResults == null || realmResults.isEmpty()) {
            return;
        }
        SpecialPageRealm specialPageRealm =
                (SpecialPageRealm) realmResults.get(0);
        if (specials.isEmpty()) {   // data is empty, network not back
            RealmList<SpecialRealm> realmList = specialPageRealm.getSpecialList();
            List<Special> specialList = SpecialPageRealm.getSpecialList(realmList);
            setSpecialList(specialList);
        }
    }

    private void setSpecialList(List<Special> specialList) {
        checkEmpty(specialList);
        if (isRefresh) {
            specials.clear();
        }
        if (!isEmpty.get()) {
            specials.addAll(specialList);
        }
    }
}
