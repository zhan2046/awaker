package com.love.awaker.data.source.remote;

import com.love.awaker.data.New;
import com.love.awaker.data.source.NewDataSource;
import com.love.awaker.network.AwakerClient;
import com.love.awaker.network.HttpResult;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright ©2017 by Teambition
 */

public class NewRemoteDataSource implements NewDataSource {

    @Override
    public Flowable<HttpResult<List<New>>> getNewList(String token, int page, int id) {
        return AwakerClient.get().getNewList(token, page, id)
                .subscribeOn(Schedulers.io());
    }
}
