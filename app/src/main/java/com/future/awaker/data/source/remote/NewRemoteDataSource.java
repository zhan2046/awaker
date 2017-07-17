package com.future.awaker.data.source.remote;

import com.future.awaker.data.New;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.data.source.NewDataSource;
import com.future.awaker.network.AwakerClient;
import com.future.awaker.network.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright ©2017 by Teambition
 */

public class NewRemoteDataSource implements NewDataSource {

    @Override
    public Observable<HttpResult<List<New>>> getNewList(String token, int page, int id) {
        return AwakerClient.get().getNewList(token, page, id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat) {
        return AwakerClient.get().getSpecialList(token, page, cat)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResult<NewDetail>> getNewDetail(String token, String newId) {
        return AwakerClient.get().getNewDetail(token, newId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id) {
        return AwakerClient.get().getSpecialDetail(token, id)
                .subscribeOn(Schedulers.io());
    }
}
