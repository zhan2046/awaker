package com.future.awaker.news.viewmodel;

import android.arch.lifecycle.LiveData;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.News;
import com.future.awaker.db.entity.NewsEntity;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.source.DataRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;



public class NewListViewModel extends BaseListViewModel {

    private int newId;
    private LiveData<List<NewsEntity>> newEntitys;
    private DataRepository repository;

    public NewListViewModel() {
        repository = DataRepository.get();
        newEntitys = repository.loadAllNewsEntitys();
    }

    public LiveData<List<NewsEntity>> getNewEntitys() {
        return newEntitys;
    }

    public void insertAll(List<NewsEntity> newsEntities) {
        repository.insertAll(newsEntities);
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    @Override
    public void refreshData(boolean refresh) {
        AwakerRepository.get().getNewList(TOKEN, page, newId)
                .map(listHttpResult -> {
                    List<News> newsList = listHttpResult.getData();
                    List<NewsEntity> newsEntities = NewsEntity.getNewsEntityList(newsList);
                    if (newsEntities != null && !newsEntities.isEmpty()) {
                        insertAll(newsEntities);
                    }
                    return newsList;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))

                .doOnNext(newsList -> {

                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }
}
