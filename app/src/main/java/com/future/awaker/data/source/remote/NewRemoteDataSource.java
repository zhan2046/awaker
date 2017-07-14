package com.future.awaker.data.source.remote;

import com.future.awaker.data.New;
import com.future.awaker.data.Video;
import com.future.awaker.data.source.NewDataSource;
import com.future.awaker.network.AwakerClient;
import com.future.awaker.network.HttpResult;

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

    @Override
    public Flowable<HttpResult<List<Video>>> getSpecialList(String token, int page, int cat) {
        return AwakerClient.get().getSpecialList(token, page, cat)
                .subscribeOn(Schedulers.io());
    }
}
